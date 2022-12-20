package project;

public class Pair<T, U> {
        private final T p1;
        private final U p2;
        public Pair(T p1, U p2) {
                this.p1 = p1;
                this.p2 = p2;
        }
        public T getP1() {
                return p1;
        }
        public U getP2() {
                return p2;
        }
        @Override
        public String toString() {
                return "Pair{" +
                               "p1=" + p1 +
                               ", p2=" + p2 +
                               '}';
        }
}