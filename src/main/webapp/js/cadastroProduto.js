document.addEventListener('DOMContentLoaded', function(){
    const vetorConsulta = ["marca","fornecedor"];
	for(const consulta of vetorConsulta){
		autoCompletar(consulta);
	}
	
	
	
	const bxestoque = document.querySelector(".containerEstoque");
	const btnestoque = document.getElementById("addEstoque");

	btnestoque.addEventListener('click', function(){
	    bxestoque.classList.toggle("containerEstoqueON");
	} );
})


async function autoCompletar(consulta){
	const url = `/api/consulta/${consulta}` 
	

    const resp = await fetch(url);
    const dadosMarca = await resp.json();

    const inputMarca = document.getElementById(consulta);
    const janelaAuto = document.getElementById("janelaAuto"+consulta);

    inputMarca.addEventListener('input', function(){
        if(this.value.toLowerCase().length === 0){
			 janelaAuto.innerHTML = ''; 
			 return;
		 }
        janelaAuto.innerHTML = '';

        const tresFiltrados = dadosMarca.filter( item => item.toLowerCase().includes(this.value.toLowerCase())).slice(0,3);

        tresFiltrados.forEach(marcas => {
                const li = document.createElement("li");
                li.classList.add('topico-item');
                li.innerHTML = marcas;

                li.addEventListener('click', function() {
                    inputMarca.value = marcas;
                    janelaAuto.innerHTML = '';
                });

                janelaAuto.appendChild(li);

        });
    })
    
    document.addEventListener('click', (e) => {
        if(e.target !== inputMarca){
            janelaAuto.innerHTML = '';
        }
    })
}





