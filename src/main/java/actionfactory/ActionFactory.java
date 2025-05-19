package actionfactory;

import action.*;

public class ActionFactory {
    public static Action getAction(String actionName) {
        return switch (actionName) {
            case "registro" -> new UserRegisterAction();
            case "login" -> new UserLoginAction();
            case "logout" -> new UserLogoutAction();
            case "usuario-listado" -> new UsuarioListadoAction();
            case "empleado-listado-json" -> new UsuarioListadoAction();
            case "hamburguesas" -> new HamburguesasAction();
            case "bebidas" -> new BebidasAction();
            case "postres" -> new PostreAction();
            case "guarniciones" -> new GuarnicionesAction();
            case "addToCart" -> new AddToCartAction();
            case "eliminarProductoCarrito" -> new EliminarProductoCarritoAction();
            case "verCarrito" -> new ViewCartAction();
            //case "empleado-register" -> new EmpleadoRegisterAction();
            case "pedidos-completados" -> new PedidosCompletadosAction();
            case "EnviarMensajeAnonimo" -> new EnviarMensajeAnonimoAction();
            case "ver-buzon" -> new VerBuzonAction();
            //case "empleado-baja" -> new EmpleadoBajaAction();
            //case "empleado-editar" -> new EmpleadoEditarAction();
            case "pagarConPaypal" -> new PagarConPaypalAction();
            case "filtrarHamburguesas" -> new FiltrarHamburguesasAction();
            case "getUsuarioLogueado" -> new GetUsuarioLogueadoAction();
            case "getHamburguesasJSON" -> new GetHamburguesasJSONAction();
            case "getGuarniciones" -> new GetGuarnicionesJSONAction();
            case "getBebidas" -> new GetBebidasJSONAction();
            case "getPostres" -> new GetPostresJSONAction();
            case "getResumenCompra" -> new GetResumenCompraAction();
            case "actualizarRanking" -> new ActualizarRankingAction();
            case "isUserLoggedIn" -> new IsUserLoggedInAction();
            case "actualizarCantidadCarrito" -> new ActualizarCantidadCarritoAction();


            default -> new UnknownAction();
        };
    }
}
