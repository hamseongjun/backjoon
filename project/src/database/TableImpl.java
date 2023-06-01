package database;

import java.util.List;
import java.util.function.Predicate;

class TableImpl implements Table {
    private String name;
    private List<Column> columns;

    TableImpl(String name, List<Column> columns) {
        this.name = name;
        this.columns = columns;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void show() {

    }
//
//    @Override
//    public void describe() {
//
//    }
//
//    @Override
//    public Table head() {}
//
//    @Override
//    public Table head(int lineCount) {}
//
//    @Override
//    public Table tail() {}
//
//    @Override
//    public Table tail(int lineCount) {}
//
//    @Override
//    public Table selectRows(int beginIndex, int endIndex) {}
//
//    @Override
//    public Table selectRowsAt(int ...indices) {}
//
//    @Override
//    public Table selectColumns(int beginIndex, int endIndex) {}
//
//    @Override
//    public Table selectColumnsAt(int ...indices) {}
//
//    @Override
//    public <T> Table selectRowsBy(String columnName, Predicate<T> predicate) {}
//
//    @Override
//    public Table sort(int byIndexOfColumn, boolean isAscending, boolean isNullFirst) {}
//
//    @Override
//    public int getRowCount() {}
//
//    @Override
//    public int getColumnCount() {}
//
//    @Override
//    public Column getColumn(int index) {}
//
//    @Override
//    public Column getColumn(String name) {}
//
//    @Override
//    public Table crossJoin(Table rightTable) {}
//
//    @Override
//    public Table innerJoin(Table rightTable, List<JoinColumn> joinColumns) {}
//
//    @Override
//    public Table outerJoin(Table rightTable, List<JoinColumn> joinColumns) {}
//
//    @Override
//    public Table fullOuterJoin(Table rightTable, List<JoinColumn> joinColumns) {}

}
