const notiBtn = document.getElementById('notiBtn');
const notiDropdown = document.getElementById('notiDropdown');
const notiBadge = document.getElementById('notiBadge');
const notiHistorico = document.getElementById('notiHistorico');


function inicializarInterface() {
    const historicoHTML = sessionStorage.getItem('historicoHTML') || '';
    notiHistorico.innerHTML = historicoHTML;

    const naoLidas = parseInt(sessionStorage.getItem('contadorNaoLidas') || '0');
    const esconderBadge = sessionStorage.getItem('esconderBadge') === 'true';

    if (naoLidas > 0 && !esconderBadge) {
        notiBadge.textContent = naoLidas;
        notiBadge.classList.remove('hidden');
    } else {
        notiBadge.classList.add('hidden');
    }
}


function processarAtualizacaoEstoque(produtosNovos) {
    
    const estoqueAnterior = JSON.parse(sessionStorage.getItem('estadoEstoque') || '{}');
    const novoEstoque = {};

    let novosAlertasGerados = 0;
    let htmlNovasNotificacoes = '';

    produtosNovos.forEach(produto => {
        const qtdNova = parseInt(produto.quantidade);
        const min = parseInt(produto.quantidadeMin);
        const nome = produto.nome; 

        
        novoEstoque[nome] = qtdNova;

        
        const estaCritico = qtdNova < min;
        const estaAlerta = min <= qtdNova && qtdNova <= min + 5;

        if (estaCritico || estaAlerta) {
            
            const qtdAntiga = estoqueAnterior[nome];
            const ehNovaNotificacao = (qtdAntiga === undefined) || (qtdNova < qtdAntiga);

            if (ehNovaNotificacao) {
                novosAlertasGerados++;

                let mensagem = '';
                if (estaCritico) {
                    mensagem = `<strong>${produto.nome}:</strong> Estoque <span style="color: var(--red-aviso);">crítico!</span> Restam apenas ${qtdNova} unidades.`;
                } else {
                    mensagem = `<strong>${produto.nome}:</strong> Estoque <span style="color: var(--color-warning);">acabando!</span> ${qtdNova} unidades em estoque.`;
                }

                htmlNovasNotificacoes += `
                    <div class="notification-item unread">
                        <div class="unread-dot"></div>
                        <div class="notification-content">
                            <p class="notification-text">${mensagem}</p>
                        </div>
                    </div>
                `;
            }
        }
    });

    
    if (novosAlertasGerados > 0) {
        notiHistorico.insertAdjacentHTML('afterbegin', htmlNovasNotificacoes);
        sessionStorage.setItem('historicoHTML', notiHistorico.innerHTML);

        
        let contadorAtual = parseInt(sessionStorage.getItem('contadorNaoLidas') || '0');
        contadorAtual += novosAlertasGerados;
        sessionStorage.setItem('contadorNaoLidas', contadorAtual);
        sessionStorage.setItem('esconderBadge', 'false'); 

        
        if (!notiDropdown.classList.contains('open')) {
            notiBadge.textContent = contadorAtual;
            notiBadge.classList.remove('hidden');
        }
    }

    
    sessionStorage.setItem('estadoEstoque', JSON.stringify(novoEstoque));
}

async function verificarProdutosDoServidor() {
    try {
        const url = `/api/consulta/produto`;
        const response = await fetch(url);
        const dados = await response.json();
        
        processarAtualizacaoEstoque(dados);
    } catch (error) {
        console.error("Erro ao consultar produtos da API:", error);
    }
}

document.addEventListener("DOMContentLoaded", function() {

    inicializarInterface();
    
    verificarProdutosDoServidor();

    
    notiBtn.addEventListener('click', (e) => {
        e.stopPropagation(); 
        notiDropdown.classList.toggle('open');
        
        if (notiDropdown.classList.contains('open')) {
            notiBadge.classList.add('hidden');
            sessionStorage.setItem('esconderBadge', 'true');
            sessionStorage.setItem('contadorNaoLidas', '0');
        }
    });

    // Fechar ao clicar fora
    document.addEventListener('click', (e) => {
        if (!notiDropdown.contains(e.target) && e.target !== notiBtn) {
            notiDropdown.classList.remove('open');
        }
    });
});
