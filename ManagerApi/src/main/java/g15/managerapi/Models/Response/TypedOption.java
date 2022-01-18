package g15.managerapi.Models.Response;

/**
 * @author Oliver Ro Møltoft Christensen s176352
 */
public class TypedOption<T> extends Option{
    public final T model;

    TypedOption(boolean completed, String message, T model) {
        super(completed, message);
        this.model = model;
    }
}
