package implementation;

import com.rabbitmq.client.Delivery;

public class Message<TModel> {
    public Delivery delivery;
    public TModel model;

    public Message(Delivery delivery, TModel model) {
        this.delivery = delivery;
        this.model = model;
    }

    public static <T> Message<T> from(Delivery delivery, T model){
        return new Message<>(delivery, model);
    }
    public <T> Message<T> update(T newModel){
        System.out.println("Message updated from type "+ model.getClass().getName() + " to " + newModel.getClass().getName());
        return new Message<>(delivery, newModel);
    }
}
