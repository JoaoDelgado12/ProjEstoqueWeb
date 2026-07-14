async function filtroEstoque(){
    try{
        const nome = document.getElementById("pesquisarNome").value.toLowerCase()
        const marca = document.getElementById("filtroMarca").value
        const data  = document.getElementById("filtroData").value
		
		
        const url = `/api/estoque/filtro?nome=${encodeURIComponent(nome)}&marca=${encodeURIComponent(marca)}&data=${encodeURIComponent(data)}`

		
		const response = await fetch(url)
        const dados = await response.json()
        
        const tabela = document.getElementById("corpoTabela")
        
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
        console.error(erro)
    }
}

document.getElementById("btn-pesquisar").addEventListener("click", filtroEstoque);