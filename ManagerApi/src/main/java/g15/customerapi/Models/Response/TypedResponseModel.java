package g15.customerapi.Models.Response;

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
