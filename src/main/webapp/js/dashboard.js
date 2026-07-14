async function carregarEstoque(){
    try {
        const response = await fetch("/api/estoque/all");
        const dados = await response.json();
        const tabela = document.getElementById("corpoTabela");
        tabela.innerHTML= "";
		let linhas="";
		
		dados.forEach(item => {
            linhas += `
            <tr>
                <td>${item.nome}</td>
                <td>${item.quantidade || 0}</td>
                <td>${item.marca}</td>
				<td>${parseFloat(item.precoVendaUni || 0).toFixed(2)}</td>
                <td>${parseFloat(item.desconto || 0).toFixed(2)}</td>
                <td>${parseFloat(item.precoTotal || 0).toFixed(2)}</td>
            </tr>
            `
        })
		tabela.innerHTML = linhas;
    }catch(erro){
        console.log("Erro ao carregar os produtos", erro);
    }
}


async function carregarResumo(){
    try{
        const response = await fetch("/api/estoque/resumo");
        const dados = await response.json();

        document.getElementById("cardEntrada").innerHTML = dados.entrada;
        document.getElementById("cardSaida").innerHTML = dados.saida;
        const cardTotal = document.getElementById("cardTotal").innerHTML = dados.total;
		
		document.querySelector('.card-Total-Positivo').classList.toggle('card-Total-Negativo', dados.total < 0);
		document.querySelector('.card-Total-Negativo').classList.toggle('card-Total-Positivo', dados.total >= 0);

		
    }catch(erro){
        console.log("Erro da consulta dos dados", erro);

    }
}

window.onload = () => {
    carregarEstoque();
    carregarResumo();
	
	const pagina = document.getElementById("pagina");
	    pagina.addEventListener("input", function(){
	        this.value = this.value.replace(/\D/g,"");
	});
}