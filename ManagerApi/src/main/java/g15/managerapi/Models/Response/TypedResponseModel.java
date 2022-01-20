package g15.managerapi.Models.Response;

/**
 * @author Søren Andersen s182881
 * @author Oliver Ro Møltoft Christensen s176352
 */
public class TypedResponseModel<T> extends ResponseModel {
    public T model;

    public static <T> TypedResponseModel<T> fromOption(TypedOption<T> option){
        var response = new TypedResponseModel<T>();
        response.completed = option.completed;
        response.message = option.message;
        response.model = option.model;
        return response;
    }
}
