import {
    BrowserRouter,
    Routes,
    Route,
    Navigate
} from "react-router-dom";

import LoginPage from "./pages/LoginPage";
import ProductListPage from "./pages/ProductListPage";

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

            </Routes>
        </BrowserRouter>
    );

}

export default App;