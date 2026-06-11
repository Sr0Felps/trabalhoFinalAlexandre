export interface Categoria {
    id: number;
    nome: string;
}

export interface Vinho {
    id: number;
    nome: string;
    vinicola: string;
    safra: number;
    quantidadeEstoque: number;
    categoria: Categoria;
}