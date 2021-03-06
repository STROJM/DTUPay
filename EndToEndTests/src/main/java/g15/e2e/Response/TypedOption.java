package g15.e2e.Response;

public class TypedOption<T> extends Option {
    public final T model;

    TypedOption(boolean completed, String message, T model) {
        super(completed, message);
        this.model = model;
    }
}
