import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';
import '../index.css';

export function Login() {
    const [isCadastro, setIsCadastro] = useState(false);
    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        try {
            if (isCadastro) {
                // Rota do seu back-end para registrar
                await api.post('/auth/registar', { nome, email, senha });
                alert('Cadastro realizado com sucesso! Faça o login agora.');
                setIsCadastro(false); // Volta para a tela de login
                setSenha(''); // Limpa a senha por segurança
            } else {
                // Rota do seu back-end para login
                const response = await api.post('/auth/login', { email, senha });
                localStorage.setItem('token', response.data.token);
                navigate('/vinhos');
            }
        } catch (error) {
            console.error(error);
            alert(isCadastro ? 'Erro ao cadastrar. Verifique os dados.' : 'Email ou senha incorretos!');
        }
    };

    return (
        <div className="container" style={{ maxWidth: '400px', marginTop: '10%' }}>
            <h2 style={{ textAlign: 'center' }}>{isCadastro ? 'Criar Conta' : 'Login da Adega'}</h2>

            <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', gap: '1rem', marginTop: '1.5rem' }}>
                {isCadastro && (
                    <input
                        type="text"
                        placeholder="Seu nome"
                        value={nome}
                        onChange={(e) => setNome(e.target.value)}
                        required
                        style={{ padding: '0.8rem', borderRadius: '4px', border: '1px solid #ccc' }}
                    />
                )}
                <input
                    type="email"
                    placeholder="Seu e-mail"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                    style={{ padding: '0.8rem', borderRadius: '4px', border: '1px solid #ccc' }}
                />
                <input
                    type="password"
                    placeholder="Sua senha"
                    value={senha}
                    onChange={(e) => setSenha(e.target.value)}
                    required
                    style={{ padding: '0.8rem', borderRadius: '4px', border: '1px solid #ccc' }}
                />

                <button type="submit" className="btn btn-primary" style={{ padding: '0.8rem', marginTop: '0.5rem' }}>
                    {isCadastro ? 'Cadastrar' : 'Entrar'}
                </button>
            </form>

            <button
                onClick={() => setIsCadastro(!isCadastro)}
                className="btn btn-outline"
                style={{ marginTop: '1rem', width: '100%', padding: '0.8rem' }}
            >
                {isCadastro ? 'Já tenho uma conta (Fazer Login)' : 'Não tenho conta (Cadastrar)'}
            </button>
        </div>
    );
}