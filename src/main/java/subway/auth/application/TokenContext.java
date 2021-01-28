package subway.auth.application;

public class TokenContext<T> {

    private T item;

    public TokenContext(T item) {
        this.item = item;
    }

    public T getItem(){
        return item;
    }

}
