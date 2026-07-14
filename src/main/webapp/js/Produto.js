let dadosProdutos=[];

async function gerenciarProduto(){
    const nomeProduto = event.target.getAttribute('id'); //pegando o id do gerenciar que tem o nome do produto
    
    try{
        const resp = await fetch(`/api/gerenciar/produto?nome=${encodeURIComponent(nomeProduto)}`);

    }catch(error){
        alert('Erro no gereciamento do produto ' + `${nomeProduto}`);
    }
}

async function carregarProdutos() {
    try {
        const resp = await fetch(`/api/consulta/produto`);
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
        if (parseInt(prod.quantidade) < parseInt(prod.quantidadeMin)) {
            card.classList.add('card-critico');
        } else if ( parseInt(prod.quantidade) <= parseInt(prod.quantidadeMin) + 5) {
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
                <a class="btn-link-seta" id="${prod.nome}">
                    Gerenciar;
                </a>
            </div>
        `;

        painelCards.appendChild(card);
    });
}

// Executa ao carregar
gerarCards();
})