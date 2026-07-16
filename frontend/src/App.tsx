import {
    BrowserRouter,
    Routes,
    Route,
    Navigate
} from "react-router-dom";

import LoginPage from "./pages/LoginPage";
import ProductListPage from "./pages/ProductListPage";
import ProductDetailPage from "./pages/ProductDetailPage";
import CartPage from "./pages/CartPage";
import OrderConfirmPage from "./pages/OrderConfirmPage";
import OrderHistoryPage from "./pages/OrderHistoryPage";
import OrderDetailPage from "./pages/OrderDetailPage";

function App() {

    return (
        <BrowserRouter>
            <Routes>

                <Route
                    path="/"
                    element={<Navigate to="/login" replace />}
                />

                <Route
                    path="/login"
                    element={<LoginPage />}
                />

                <Route
                    path="/products"
                    element={<ProductListPage />}
                />

                <Route
                    path="/products/:id"
                    element={<ProductDetailPage />}
                />

                <Route
                    path="/cart"
                    element={<CartPage />}
                />

                <Route
                    path="/order-confirm"
                    element={<OrderConfirmPage />}
                />

                <Route
                    path="/orders"
                    element={<OrderHistoryPage />}
                />

                <Route
                    path="/orders/:id"
                    element={<OrderDetailPage />}
                />

            </Routes>
        </BrowserRouter>
    );

}

export default App;