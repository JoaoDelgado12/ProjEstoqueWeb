let dadosProdutos=[];

async function carregarProdutos() {
    try {
        const resp = await fetch(`http://localhost:8080/api/consulta/produto`);
        const dados = await resp.json();
        
        dados.forEach(produto => {
            dadosProdutos.push({
                nome: produto.nome, 
                quantidade: produto.quantidade, 
                quantidadeMin: produto.quantidadeMin,
                precoVendaUni: produto.precoVendaUni
            });
        });
    } catch (erro) {
        console.error("Erro ao buscar produtos:", erro);
    }
}


document.addEventListener("DOMContentLoaded", async function(){

await carregarProdutos();

const painelCards = document.getElementById('painelCards');

function gerarCards() {
    painelCards.innerHTML = '';

    dadosProdutos.forEach(prod => {
        const card = document.createElement('div');
        card.classList.add('card-produto');

        if (prod.quantidade < prod.quantidadeMin) {
            card.classList.add('card-critico');
        } else if (prod.quantidade <= prod.quantidadeMin + 5) {
            card.classList.add('card-atencao');
        } else {
            card.classList.add('card-normal');
        }

        // Layout interno de cada Card
        card.innerHTML = `
            <div>
                <div class="card-titulo">${prod.nome}</div>
                <div class="card-info">Estoque Atual: <strong>${prod.quantidade} un</strong></div>
                <div class="card-info" style="font-size: 0.85rem; color: #94a3b8;">Mínimo requerido: ${prod.quantidadeMin} un</div>
            </div>
            
            <div class="card-rodape">
                <div class="card-preco">R$ ${parseFloat(prod.precoVendaUni).toFixed(2)}</div>
                <a href="detalhes.html?id=${prod.id}" class="btn-link-seta">
                    Gerenciar &rarr;
                </a>
            </div>
        `;

        painelCards.appendChild(card);
    });
}

// Executa ao carregar
gerarCards();
})