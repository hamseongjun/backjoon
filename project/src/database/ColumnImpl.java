package database;

import java.net.Inet4Address;

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
        if (values[index] == null) { return null; }
        else { return String.valueOf(values[index]); }
    }

    @Override
    public <T extends Number> T getValue(int index, Class<T> t) {
        if (values[index] == null) {
            return null;
        } else if (t == Double.class) {
            return t.cast(Double.valueOf(String.valueOf(values[index])));
        } else if (t == Long.class) {
            return t.cast(Long.valueOf(String.valueOf(values[index])));
        } else if (t == Integer.class) {
            return t.cast(Integer.valueOf(String.valueOf(values[index])));
        } else {
            throw new IllegalArgumentException("Unsupported type: " + t.getSimpleName());
        }
    }

    @Override
    public void setValue(int index, String value) {
        values[index] = value;
    }

    @Override
    public void setValue(int index, int value) {
        values[index] = value;
    }

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

    @Override
    public boolean isNumericColumn() {
        for (int i = 0; i < count(); i++) {
            if (getValue(i) == null) { continue; }
            try {
                Integer value = Integer.valueOf(getValue(i));
            } catch (NumberFormatException nonInt) {
                try {
                    Double value = Double.valueOf(getValue(i));
                }
                catch (NumberFormatException nonDouble) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public long getNullCount() {
        int count = 0;
        for (int i = 0; i < count(); i++) {
            if(values[i] != null) { count++; }
        }
        return count;
    }
}
