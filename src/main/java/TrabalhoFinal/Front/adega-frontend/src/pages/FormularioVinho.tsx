import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import api from '../services/api';
import type { Categoria } from '../types';
import '../index.css';

export function FormularioVinho() {
    const navigate = useNavigate();
    const { id } = useParams(); // Pega o ID da URL se existir

    const [nome, setNome] = useState('');
    const [vinicola, setVinicola] = useState('');
    const [safra, setSafra] = useState<number | ''>('');
    const [quantidadeEstoque, setQuantidadeEstoque] = useState<number | ''>('');
    const [categoriaId, setCategoriaId] = useState<number | ''>('');
    const [categorias, setCategorias] = useState<Categoria[]>([]);

    useEffect(() => {
        carregarCategorias();
        // Se tiver um ID na URL, carrega os dados do vinho para editar
        if (id) {
            carregarVinhoParaEdicao();
        }
    }, [id]);

    const carregarCategorias = async () => {
        try {
            const response = await api.get('/categorias');
            setCategorias(response.data);
        } catch (error) {
            console.error(error);
        }
    };

    const carregarVinhoParaEdicao = async () => {
        try {
            const response = await api.get(`/vinhos/${id}`);
            const vinho = response.data;
            setNome(vinho.nome);
            setVinicola(vinho.vinicola);
            setSafra(vinho.safra);
            setQuantidadeEstoque(vinho.quantidadeEstoque);
            setCategoriaId(vinho.categoria.id);
        } catch (error) {
            alert('Erro ao carregar os dados do vinho.');
            navigate('/vinhos');
        }
    };

    const adicionarNovaCategoria = async () => {
        const nomeCategoria = window.prompt("Digite o nome da nova Categoria (Ex: Vinho Rosé):");
        if (nomeCategoria) {
            try {
                await api.post('/categorias', { nome: nomeCategoria });
                alert('Categoria criada com sucesso!');
                carregarCategorias();
            } catch (error) {
                alert('Erro ao criar a categoria.');
            }
        }
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            const dadosVinho = {
                nome,
                vinicola,
                safra: Number(safra),
                quantidadeEstoque: Number(quantidadeEstoque),
                categoria: { id: Number(categoriaId) }
            };

            if (id) {
                // Se tem ID, atualiza (PUT)
                await api.put(`/vinhos/${id}`, dadosVinho);
                alert('Vinho atualizado com sucesso!');
            } else {
                // Se não tem ID, cria (POST)
                await api.post('/vinhos', dadosVinho);
                alert('Vinho salvo com sucesso!');
            }

            navigate('/vinhos');
        } catch (error) {
            console.error(error);
            alert('Erro ao salvar o vinho.');
        }
    };

    const inputStyle = { padding: '0.6rem', borderRadius: '4px', border: '1px solid var(--border-color)', width: '100%' };

    return (
        <div className="container" style={{ maxWidth: '600px' }}>
            <h2>{id ? '✏️ Editar Vinho' : '🍷 Adicionar Novo Vinho'}</h2>

            <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '1rem', marginTop: '1.5rem' }}>
                <div>
                    <label style={{ fontWeight: 'bold' }}>Nome do Vinho:</label>
                    <input type="text" value={nome} onChange={e => setNome(e.target.value)} required style={inputStyle} />
                </div>

                <div>
                    <label style={{ fontWeight: 'bold' }}>Vinícola:</label>
                    <input type="text" value={vinicola} onChange={e => setVinicola(e.target.value)} required style={inputStyle} />
                </div>

                <div style={{ display: 'flex', gap: '1rem' }}>
                    <div style={{ flex: 1 }}>
                        <label style={{ fontWeight: 'bold' }}>Ano (Safra):</label>
                        <input type="number" value={safra} onChange={e => setSafra(Number(e.target.value))} required style={inputStyle} />
                    </div>
                    <div style={{ flex: 1 }}>
                        <label style={{ fontWeight: 'bold' }}>Quantidade em Estoque:</label>
                        <input type="number" value={quantidadeEstoque} onChange={e => setQuantidadeEstoque(Number(e.target.value))} required style={inputStyle} />
                    </div>
                </div>

                <div>
                    <label style={{ fontWeight: 'bold' }}>Categoria:</label>
                    <div style={{ display: 'flex', gap: '0.5rem', marginTop: '0.2rem' }}>
                        <select value={categoriaId} onChange={e => setCategoriaId(Number(e.target.value))} required style={inputStyle}>
                            <option value="" disabled>Selecione uma categoria...</option>
                            {categorias.map(cat => (
                                <option key={cat.id} value={cat.id}>{cat.nome}</option>
                            ))}
                        </select>
                        <button type="button" onClick={adicionarNovaCategoria} className="btn btn-outline" style={{ whiteSpace: 'nowrap' }}>
                            + Nova Categoria
                        </button>
                    </div>
                </div>

                <div style={{ display: 'flex', gap: '1rem', marginTop: '2rem' }}>
                    <button type="submit" className="btn btn-primary" style={{ flex: 1 }}>
                        {id ? 'Salvar Alterações' : 'Salvar Vinho'}
                    </button>
                    <button type="button" onClick={() => navigate('/vinhos')} className="btn btn-outline" style={{ flex: 1 }}>Cancelar</button>
                </div>
            </form>
        </div>
    );
}