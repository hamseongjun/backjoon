package database;

class ColumnImpl implements Column {
    private String header;
    private Object[] values;

    public ColumnImpl(String header, Object[] values) {
        this.header = header;
        this.values = values;
    }

    @Override
    public String getHeader() {
        return header;
    }

    @Override
    public String getValue(int index) {
        return String.valueOf(values[index]);
    }

//    @Override
//    public <T extends Number> T getValue(int index, Class<T> t) {}
//
//    @Override
//    public void setValue(int index, String value) {}
//
//    @Override
//    public void setValue(int index, int value) {}

    @Override
    public int count() {
        return values.length;
    }

    @Override
    public void show() {
        System.out.println(header);
        for (Object value : values) {
            System.out.println(value);
        }
    }

//    @Override
//    public boolean isNumericColumn() {}
//
//    @Override
//    public long getNullCount() {}

}
