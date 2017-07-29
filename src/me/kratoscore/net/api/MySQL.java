package me.kratoscore.net.api;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import me.kratoscore.net.bungee.KratosCore;

public class MySQL {

    private static MySQL instance = new MySQL();

    public static MySQL getInstance(){
        return instance;
    }

    public Select select(){
        return new Select();
    }

    public Insert insert(){
        return new Insert();
    }

    public Update update(){
        return new Update();
    }

    public Delete delete(){
        return new Delete();
    }


    public class Delete {

        private String table, where, equals;

        public Delete table(String table) {
            this.table = table;
            return this;
        }

        public Delete where(String where) {
            this.where = where;
            return this;
        }

        public Delete equals(String equals) {
            this.equals = equals;
            return this;
        }

        public void delete() {
            try {
                PreparedStatement preparedStatement;
                StringBuilder sb = new StringBuilder();

                sb.append("DELETE FROM `");
                sb.append(table);
                sb.append("` WHERE `");
                sb.append(where);
                sb.append("` = ?;");

                preparedStatement = KratosCore.getInstance().getDatabaseManager().getConnection().prepareStatement(sb.toString());
                preparedStatement.setString(1, equals);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public enum UpdateType{SET}

    public class Update {

        private UpdateType type;
        private String column, table, where, equals, to;

        public Update type(UpdateType type) {
            this.type = type;
            return this;
        }

        public Update to(String to){
            this.to = to;
            return this;
        }

        public Update table(String table) {
            this.table = table;
            return this;
        }

        public Update column(String column) {
            this.column = column;
            return this;
        }

        public Update where(String where) {
            this.where = where;
            return this;
        }

        public Update equals(String equals) {
            this.equals = equals;
            return this;
        }

        public void update() {
            try {
                PreparedStatement preparedStatement;
                StringBuilder sb = new StringBuilder();

                sb.append("UPDATE `");
                sb.append(table);
                sb.append("` " + type.toString() + " `");

                sb.append(column);
                sb.append("` = ? ");
                sb.append(" WHERE `");
                sb.append(where);
                sb.append("` = ?;");

                preparedStatement = KratosCore.getInstance().getDatabaseManager().getConnection().prepareStatement(sb.toString());
                preparedStatement.setString(1, to);
                preparedStatement.setString(2, equals);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public class Insert {

        public InsertSingle single(){
            return new InsertSingle();
        }

        public class InsertSingle {

            private String table,column,value;

            public InsertSingle table(String table){
                this.table = table;
                return this;
            }

            public InsertSingle column(String column){
                this.column = column;
                return this;
            }

            public InsertSingle value(String value){
                this.value = value;
                return this;
            }

            public void insert(){
                try {
                    PreparedStatement preparedStatement;

                    StringBuilder sb = new StringBuilder();
                    sb.append("INSERT INTO ");
                    sb.append(table);
                    sb.append("(");
                    sb.append("`" + column + "`");
                    sb.append(") VALUES (");
                    sb.append("'" + value + "'");
                    sb.append(");");

                    preparedStatement = KratosCore.getInstance().getDatabaseManager().getConnection().prepareStatement(sb.toString());
                    preparedStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        private String table;
        private List<String> columns;
        private List<Object> values;

        public Insert table(String table){
            this.table = table;
            return this;
        }

        public Insert columns(List<String> columns){
            this.columns = columns;
            return this;
        }

        public Insert values(List<Object> values){
            this.values = values;
            return this;
        }

        public void insert(){
            try {
                PreparedStatement preparedStatement;
                String cfirst = columns.get(0);
                columns.remove(cfirst);

                Object vfirst = values.get(0);
                values.remove(vfirst);

                StringBuilder sb = new StringBuilder();
                sb.append("INSERT INTO ");
                sb.append(table);
                sb.append("(");
                sb.append("`" + cfirst + "`");
                for(String column : columns){
                    sb.append(", `" + column + "`");
                }
                sb.append(") VALUES (");
                sb.append("'" + vfirst + "'");
                for(Object value : values){
                    sb.append(", '" + value + "'");
                }
                sb.append(");");

                preparedStatement = KratosCore.getInstance().getDatabaseManager().getConnection().prepareStatement(sb.toString());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public enum WhereType{EQUALS("="),HIGHER(">"),LOWER("<"),LOWEROREQUALS("<="),HIGHEROREQUALS(">="),LOWERORHIGHER("<>");
        private String sql;
        WhereType(String sql){
            this.sql = sql;
        }
        public String getSQL(){
            return sql;
        }
    }
    public class Select {

        private String column,table,where,equals;
        private WhereType type;

        public Select table(String table){
            this.table = table;
            return this;
        }

        public Select column(String column){
            this.column = column;
            return this;
        }

        public Select where(String where){
            this.where = where;
            return this;
        }

        public Select wheretype(WhereType type){
            this.type = type;
            return this;
        }

        public Select wherereq(String equals){
            this.equals = equals;
            return this;
        }

        public ResultSet select(){
            try {
                PreparedStatement preparedStatement;
                StringBuilder sb = new StringBuilder();

                if(column.equals("*")){
                    sb.append("SELECT ");
                    sb.append(column);
                    sb.append(" FROM `");
                } else {
                    sb.append("SELECT `");
                    sb.append(column);
                    sb.append("` FROM `");
                }
                sb.append(table);
                if(where != null){
                    sb.append("` WHERE `");
                    sb.append(where);

                    sb.append("` ");
                    sb.append(type.getSQL());
                    sb.append(" ?;");
                } else {
                    sb.append("`;");
                }


                preparedStatement = KratosCore.getInstance().getDatabaseManager().getConnection().prepareStatement(sb.toString());

                if(equals != null){
                    preparedStatement.setString(1, equals);
                }
                preparedStatement.executeQuery();

                return preparedStatement.getResultSet();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}