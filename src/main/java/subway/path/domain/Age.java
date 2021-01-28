package subway.path.domain;

public interface Age {

    int getValue();

    static Age of(int age) {
        return new SimpleAge(age);
    }

    static Age adult() {
        return new SimpleAge(19);
    }

    class SimpleAge implements Age {

        private int age;

        public SimpleAge(int age) {
            this.age = age;
        }

        @Override
        public int getValue() {
            return age;
        }
    }
}


