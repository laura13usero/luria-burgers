package actionfactory;

import action.*;

public class ActionFactory {
    public static Action getAction(String actionName) {
        return switch (actionName) {
            case "registro" -> new UserRegisterAction();
            case "login" -> new UserLoginAction();
            case "logout" -> new UserLogoutAction();
            case "usuario-listado" -> new UsuarioListadoAction();
            case "hamburguesas" -> new HamburguesasAction();
            case "bebidas" -> new BebidasAction();
            case "postres" -> new PostreAction();
            case "guarniciones" -> new GuarnicionesAction(); // Añadido para guarniciones
            case "addToCart" -> new AddToCartAction();
            case "eliminarProductoCarrito" -> new EliminarProductoCarritoAction();
            case "verCarrito" -> new ViewCartAction();
            default -> new UnknownAction();
        };
    }
}
