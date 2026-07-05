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

            </Routes>
        </BrowserRouter>
    );

}

export default App;