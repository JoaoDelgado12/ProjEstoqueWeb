const inputNome = document.getElementById('nome');
const inputQuantidade = document.getElementById('quantidade');
const inputDesconto = document.getElementById('desconto');

const lblTotalItem = document.getElementById('lblTotalItem');
const lblTotalGeral = document.getElementById('lblTotalGeral');

const btnAdicionar = document.getElementById('btnAdicionar');
const carrinhoLista = document.getElementById('carrinhoLista');
const carrinhoVazio = document.getElementById('carrinhoVazio');
const btnSubmit = document.getElementById('btn-submit');

let carrinho = [];
let dados;

async function autoCompletar(){
	const url = `http://localhost:8080/api/consulta/produto` 
	
    const resp = await fetch(url);
    dados = await resp.json();

    const inputNome = document.getElementById("nome");
    const janelaAuto = document.getElementById("janelaAuto");

    inputNome.addEventListener('input', function(){
        if(this.value.toLowerCase().length === 0){
			 janelaAuto.innerHTML = ''; 
			 return;
		 }
        janelaAuto.innerHTML = '';
        
        const tresFiltrados = dados.filter( item => item?.nome?.toLowerCase().includes(this.value.toLowerCase())).slice(0,3);

        tresFiltrados.forEach(produto => {
                const li = document.createElement("li");
                li.classList.add('topico-item');
                li.innerHTML = produto.nome;

                li.addEventListener('click', function() {
                    inputNome.value = produto.nome;
                    calcularTotalItem();
                    janelaAuto.innerHTML = '';
                });

                janelaAuto.appendChild(li);

        });
    })
    
    document.addEventListener('click', (e) => {
        if(e.target !== inputNome){
            janelaAuto.innerHTML = '';
        }
    })
}

inputNome.addEventListener('input', autoCompletar);

function calcularTotalItem() {
    let valorProduto;
    let quantidadeProduto;
    dados.forEach(produto => {
        if(inputNome.value.toLowerCase().includes(produto.nome.toLowerCase())){
            valorProduto = produto.precoVendaUni;
            quantidadeProduto = produto.quantidade;
        }
    })
    const preco = parseFloat(valorProduto) || 0;
    const qtd = parseInt(inputQuantidade.value) || 0;
    const desc = parseFloat(inputDesconto.value) || 0;

    if (quantidadeProduto - inputQuantidade.value < 0){
        alert(`Estoque insuficiente: ${quantidadeProduto} em estoque`);
        limparCamposProduto
        return;
    }
  
    let total = (preco * qtd) - desc;
    if (total < 0) total = 0; 

    lblTotalItem.innerText = total.toFixed(2);
    return total;
}

[inputQuantidade, inputDesconto].forEach(input => {
    input.addEventListener('input', calcularTotalItem);
});

btnAdicionar.addEventListener('click', () => {

    if (!inputNome.value.trim() || inputQuantidade.value < 1) {
        alert('Por favor, preencha corretamente os campos do produto antes de adicionar.');
        limparCamposProduto
        return;
    }


    const totalItem = calcularTotalItem();

    // Objeto do Item
    const novoItem = {
        nome: inputNome.value,
        quantidade: parseInt(inputQuantidade.value),
        desconto: parseFloat(inputDesconto.value) || 0,
        total: totalItem
    };

    carrinho.push(novoItem);
    atualizarInterfaceCarrinho();
    limparCamposProduto();
});


function atualizarInterfaceCarrinho() {

    if(carrinho.length > 0) {
        carrinhoVazio.style.display = 'none';
    } else {
        carrinhoVazio.style.display = 'block';
    }

    carrinhoLista.innerHTML = '';
    if (carrinho.length === 0) carrinhoLista.appendChild(carrinhoVazio);

    let totalGeral = 0;

    carrinho.forEach((item, index) => {
        totalGeral += item.total;

        const li = document.createElement('li');
        li.classList.add('carrinho-item');
        li.innerHTML = `
            <div class="item-info">
                <strong>${item.nome}</strong>
                <span>Qtd: ${item.quantidade} | Desc: R$ ${parseFloat(item.desconto).toFixed(2)}</span>
            </div>
            <div class="container-preco-remover">
                <div class="item-preco">R$ ${item.total.toFixed(2)}</div>
                <div class="btn-remover" title="Remover item">&times;</div>
            </div>
        `;

        const btnRemover = li.querySelector('.btn-remover');
        btnRemover.addEventListener('click', () => {
            carrinho.splice(index, 1); 
            
            li.style.animation = 'surgir 0.2s reverse forwards';
            
            // Aguarda a animação terminar e atualiza o carrinho na tela
            setTimeout(() => {
                atualizarInterfaceCarrinho();
            }, 200);
        });
        
        carrinhoLista.appendChild(li);
    });

    lblTotalGeral.innerText = totalGeral.toFixed(2);

}

function limparCamposProduto() {
    inputNome.value = '';
    inputQuantidade.value = '1';
    inputDesconto.value = '0.00';
    lblTotalItem.innerText = '0.00';
    inputNome.focus();
}

// Tratamento do Envio (POST)
btnSubmit.addEventListener('click', async () => {
    try{
		if(carrinho.length === 0) {
	        alert('Seu carrinho está vazio! Adicione pelo menos um item para finalizar a compra.');
            limparCamposProduto
	    	return 
		}
		
		const params = new URLSearchParams();
	    params.append('dadosCarrinho', JSON.stringify(carrinho));
		
		const resp = await fetch(`/api/venda`, {
			method: 'POST',
			headers:{
			  'Content-Type': 'application/x-www-form-urlencoded'
			},
			body: params.toString()
		})
		
		if (resp.ok) {
	        alert('Venda realizada com sucesso!');
	        sessionStorage.setItem('naoAtualizaNotificacao', false);
	        
	        window.location.reload(); 
	    } else {
	        alert('Erro ao processar a venda no servidor. Venda não realizada.');
            limparCamposProduto
	    }
	}catch(erro){
		alert('Não foi possível conectar ao servidor.');
	}
	
});