const notiBtn = document.getElementById('notiBtn');
const notiDropdown = document.getElementById('notiDropdown');
const notiBadge = document.getElementById('notiBadge');
const notiHistorico = document.getElementById('notiHistorico');
let contadorNaoLidas;

function carregarNotificacoesDaSessao() {
    const notificacoesSalvas = sessionStorage.getItem('historicoNotificacoes');
    atualizarBadge();
    if (notificacoesSalvas) {
        const lista = JSON.parse(notificacoesSalvas);
        
        notiHistorico.innerHTML = '';
        
        criarNoticacao(lista, true);
    }
}

function salvarNaSessao(dados) {
    let listaAtual = [];
    const dadosAntigos = sessionStorage.getItem('historicoNotificacoes');
    sessionStorage.setItem('naoAtualizaNotificao', true)
    if (dadosAntigos) {
        listaAtual = JSON.parse(dadosAntigos);
    }

    dados.forEach(produto => {
        listaAtual.unshift({ 
            nome: produto.nome, 
            quantidade: produto.quantidade, 
            quantidadeMin: produto.quantidadeMin 
        });
    });

    sessionStorage.setItem('historicoNotificacoes', JSON.stringify(listaAtual));
}

function atualizarBadge(){
    if (!notiDropdown.classList.contains('open')) {
        notiBadge.textContent = sessionStorage.getItem('contadorNaoLidas');
        notiBadge.classList.remove('hidden');
    }
}

function criarNoticacao(dados, Historico = false){
    dados.forEach(produto => {
        if(parseInt(produto.quantidade) < parseInt(produto.quantidadeMin)){
            const novoItem = document.createElement('div');
            novoItem.className = 'notification-item';
            novoItem.innerHTML = `
                <div class="unread-dot"></div>
                <div class="notification-content">
                    <p class="notification-text"><strong>${produto.nome}:</strong> Estoque <span style="color: var(--red-aviso);"> crítico! </span> Restam apenas ${produto.quantidade} unidades.</p>
                </div>
            `;
            if (Historico) {
                notiHistorico.appendChild(novoItem);
            } else {
                notiHistorico.insertBefore(novoItem, notiHistorico.firstChild);
            }
            notiHistorico.scrollTop = 0;
        }else if(parseInt(produto.quantidadeMin) <= parseInt(produto.quantidade) < 5 + parseInt(produto.quantidadeMin)){
            const novoItem = document.createElement('div');
            novoItem.className = 'notification-item';
            novoItem.innerHTML = `
                <div class="unread-dot"></div>
                <div class="notification-content">
                    <p class="notification-text"><strong>${produto.nome}:</strong> Estoque <span style="color: var(--color-warning);">acabando! </span> ${produto.quantidade} unidades em estoque.</p>
                </div>
            `;
            if (Historico) {
                notiHistorico.appendChild(novoItem);
            } else {
                notiHistorico.insertBefore(novoItem, notiHistorico.firstChild);
            }
            notiHistorico.scrollTop = 0;
        }
   });
}

async function NovaNotificacao() {
    const url = `http://localhost:8080/api/consulta/produto`
    const response = await fetch(url);
    const dados = await response.json();

    contadorNaoLidas = dados.length;
    sessionStorage.setItem('contadorNaoLidas', contadorNaoLidas);
    atualizarBadge()
    salvarNaSessao(dados)
    criarNoticacao(dados);
}

document.addEventListener("DOMContentLoaded", function(){

    carregarNotificacoesDaSessao();
    if(!sessionStorage.getItem('naoAtualizaNotificao')){
        NovaNotificacao();
    }
    
    

    notiBtn.addEventListener('click', (e) => {
        e.stopPropagation(); 
        notiDropdown.classList.toggle('open');
        
        if (notiDropdown.classList.contains('open')) {
            notiBadge.classList.add('hidden');
            sessionStorage.setItem('contadorNaoLidas', 0);
        }
    });

    document.addEventListener('click', (e) => {
        if (!notiDropdown.contains(e.target) && e.target !== notiBtn) {
            notiDropdown.classList.remove('open');
        }
    });
})