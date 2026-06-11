import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import { Login } from './pages/Login';
import { ListagemVinhos } from './pages/ListagemVinhos';
import { FormularioVinho } from './pages/FormularioVinho';

function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Navigate to="/login" />} />
                <Route path="/login" element={<Login />} />
                <Route path="/vinhos" element={<ListagemVinhos />} />
                <Route path="/novo-vinho" element={<FormularioVinho />} />
                <Route path="/editar-vinho/:id" element={<FormularioVinho />} /> {/* Nova Rota */}
            </Routes>
        </BrowserRouter>
    );
}

export default App;