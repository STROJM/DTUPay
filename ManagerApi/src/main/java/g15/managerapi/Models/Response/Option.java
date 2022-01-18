package g15.managerapi.Models.Response;

/**
 * @author Oliver Ro Møltoft Christensen s176352
 */
public class Option {
    public final boolean completed;
    public final String message;

    Option(boolean completed, String message) {
        this.completed = completed;
        this.message = message;
    }

    public static Option success(){
        return new Option(true, null);
    }
    public static Option fail(String message){
        return new Option(false, message);
    }
    public static <T> TypedOption<T> success(T model){
        return new TypedOption<>(true, null, model);
    }
    public static <T> TypedOption<T> fail(String message, T model){
        return new TypedOption<>(false, message, model);
    }
}

