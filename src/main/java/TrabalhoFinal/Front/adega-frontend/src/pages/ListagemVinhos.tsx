import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';
import type {Vinho} from '../types';
import '../index.css';

export function ListagemVinhos() {
    const [vinhos, setVinhos] = useState<Vinho[]>([]);
    const [busca, setBusca] = useState(''); // Estado da barra de pesquisa
    const navigate = useNavigate();

    useEffect(() => {
        carregarVinhos();
    }, []);

    const carregarVinhos = async () => {
        try {
            const response = await api.get('/vinhos');
            setVinhos(response.data);
        } catch (error: any) {
            if (error.response && error.response.status === 403) {
                alert('Sessão expirada. Por favor, faz login novamente.');
                navigate('/login');
            }
        }
    };

    const fazerLogout = () => {
        localStorage.removeItem('token');
        navigate('/login');
    };

    const excluirVinho = async (id: number) => {
        const confirmar = window.confirm('Tem certeza que deseja excluir este vinho?');
        if (confirmar) {
            try {
                await api.delete(`/vinhos/${id}`);
                carregarVinhos();
            } catch (error) {
                alert('Erro ao excluir o vinho.');
            }
        }
    };

    // Filtra os vinhos com base no que foi digitado na busca
    const vinhosFiltrados = vinhos.filter(vinho =>
        vinho.nome.toLowerCase().includes(busca.toLowerCase()) ||
        vinho.categoria.nome.toLowerCase().includes(busca.toLowerCase())
    );

    return (
        <div className="container">
            <div className="cabecalho">
                <div>
                    <h2 style={{ margin: 0 }}>🍷 Grand Adega</h2>
                    <span style={{ color: 'var(--text-muted)', fontSize: '0.9rem' }}>Gestão de Estoque Premium</span>
                </div>
                <button onClick={fazerLogout} className="btn btn-outline">Sair 👋</button>
            </div>

            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1.5rem', flexWrap: 'wrap', gap: '1rem' }}>
                {/* Barra de Pesquisa */}
                <input
                    type="text"
                    placeholder="🔍 Buscar vinho ou categoria..."
                    value={busca}
                    onChange={(e) => setBusca(e.target.value)}
                    style={{ flex: 1, maxWidth: '400px' }}
                />

                <button onClick={() => navigate('/novo-vinho')} className="btn btn-primary">
                    ➕ Adicionar Rótulo
                </button>
            </div>

            <div className="tabela-wrapper">
                <table className="tabela-adega">
                    <thead>
                    <tr>
                        <th>Rótulo</th>
                        <th>Vinícola</th>
                        <th>Safra</th>
                        <th>Estoque</th>
                        <th>Categoria</th>
                        <th>Ações</th>
                    </tr>
                    </thead>
                    <tbody>
                    {vinhosFiltrados.length === 0 ? (
                        <tr>
                            <td colSpan={6} style={{ textAlign: 'center', padding: '3rem', color: 'var(--text-muted)' }}>
                                <em>Nenhum vinho encontrado.</em>
                            </td>
                        </tr>
                    ) : (
                        vinhosFiltrados.map((vinho) => (
                            <tr key={vinho.id}>
                                <td style={{ fontWeight: 600 }}>{vinho.nome}</td>
                                <td>{vinho.vinicola}</td>
                                <td>{vinho.safra}</td>
                                <td><span className="badge">{vinho.quantidadeEstoque} un.</span></td>
                                <td>{vinho.categoria.nome}</td>
                                <td style={{ display: 'flex', gap: '0.5rem' }}>
                                    {/* Botão de Editar */}
                                    <button onClick={() => navigate(`/editar-vinho/${vinho.id}`)} className="btn btn-outline" style={{ padding: '0.4rem 0.6rem', fontSize: '0.85rem' }}>
                                        ✏️
                                    </button>
                                    {/* Botão de Excluir */}
                                    <button onClick={() => excluirVinho(vinho.id)} className="btn btn-danger" style={{ padding: '0.4rem 0.6rem', fontSize: '0.85rem' }}>
                                        🗑️
                                    </button>
                                </td>
                            </tr>
                        ))
                    )}
                    </tbody>
                </table>
            </div>
        </div>
    );
}