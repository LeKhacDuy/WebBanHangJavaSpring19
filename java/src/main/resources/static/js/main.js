
sessionStorage.setItem('name', "");
sessionStorage.setItem('Id', "");




const taitrang=(id)=>{
    window.location.href=`info?id=${id}`;

}
const btninfo=document.querySelectorAll('.btn-info');
btninfo.forEach(btn=> {

    const id = btn.parentElement.getAttribute('data-id');
    btn.addEventListener('click', () => {

        taitrang(id);
    });
})
const inputsearch=document.querySelector('.input-search');
const searchbtn=document.querySelector('#search-btn');
const showsearch=document.querySelector('#show-data-search');

const moneyfilter=document.querySelectorAll('.moneyfilter');
const shoescontainer=document.getElementById('shoescontainer')
const typefilter=document.querySelectorAll('.typefilter')
const colorfilter=document.querySelectorAll('.colorfilter')
const brandfilter=document.querySelectorAll('.brandfilter')
const sortfilter=document.querySelectorAll('.sortfilter')
const filterdata={}

searchbtn?.addEventListener('click', () => {
    const data = inputsearch.value.trim();
    if (data) {
        sessionStorage.setItem('name', data);
        fetchfilter(filterdata.money,filterdata.type,filterdata.color,filterdata.sort,filterdata.brand);
    }
})

function render(data) {
    shoescontainer.innerHTML = '';
    data?.forEach(item => {
        shoescontainer.insertAdjacentHTML('beforeend', `
                     <div class="card "  >
                            <img src="${item.image}" alt="Shoe" style="width:100%" >
                            <h3 >${item.name}</h3>
                            <p class="price" >${item.price}</p>
                            <p data-id = "${item.id}"><button onclick="taitrang(${item.id})" class="btn-info" >Learn More</button></p>
                        </div>
                    `)
    })
}
function fetchfilter(money=-1,type="",color="",sort="",brand=""){
    let temp = sessionStorage.getItem('name');
    let name = "";
    if(temp!=null){
        name = temp;
    }
    fetch(`/filter?money=${money}&type=${type}&color=${color}&brand=${brand}&sort=${sort}&name=${name}`)

        .then(res => res.json())
        .then(data => {
            render(data);
            console.log(data)
        })
}



moneyfilter.forEach(x=> {
    x.addEventListener('click', () => {
        const money = x.value;
        filterdata.money=money;
        fetchfilter(filterdata.money,filterdata.type,filterdata.color,filterdata.sort,filterdata.brand)
    })
})

typefilter.forEach(x=> {
    x.addEventListener('click', () => {
        const type = x.value;
        filterdata.type = type;
        fetchfilter(filterdata.money,filterdata.type,filterdata.color,filterdata.sort,filterdata.brand)
    })
})

colorfilter.forEach(x=>{
    x.addEventListener('click',()=>{
        const color = x.value;
        filterdata.color = color;
        fetchfilter(filterdata.money,filterdata.type,filterdata.color,filterdata.sort,filterdata.brand)
    })
})

brandfilter.forEach(x=>{
    x.addEventListener('click',()=>{
        const brand = x.value;
        filterdata.brand = brand;
        fetchfilter(filterdata.money,filterdata.type,filterdata.color,filterdata.sort,filterdata.brand)

    })

})

sortfilter.forEach(x=>{
    x.addEventListener('click',()=>{
        const sort = x.value;
        filterdata.sort = sort;
        fetchfilter(filterdata.money,filterdata.type,filterdata.color,filterdata.sort,filterdata.brand)

    })

})








const cartcontainer=document.querySelector('#cart-container');
function rendercart(data){
    cartcontainer.innerHTML='';
    data.data[0].shoesDetails.forEach(item=>{
        cartcontainer.insertAdjacentHTML('beforeend',`
            <div class="cart-item" id="item">
        <img src="${item.shoes.image}" alt="" />
        <p>${item.shoes.name}</p>
        <p>${item.shoes.price}</p>
        
        <button id="remove" class="remove" onclick="deleteCartDetail(${item.shoes.id})">
          <i class="fas fa-trash fa-2x"></i>
        </button>
      </div>
        `)
    })

}

function deleteCartDetail(id){
    const temp= sessionStorage.getItem('Id');
    fetch('/deleteCartDetail'+`?cartId=${temp}&shoeId=${id}`,{method:'DELETE'})
        .then(res => res.json())
        .then(data => {
            rendercart(data)
        })
    location.reload();
}

function getCartData(status){
    const id=sessionStorage.getItem('Id');
    if (status==0){
        fetch('/getCart'+`?status=${status}`)
            .then(res => res.json())
            .then(data => {
                if(data.length==0){
                    console.log(sessionStorage.getItem('Id'))
                    if(sessionStorage.getItem('Id')!=null){
                        sessionStorage.removeItem('Id');
                    }
                    console.log(sessionStorage.getItem('Id'))
                }
                if(data.data.length!=0){
                   sessionStorage.setItem('Id',data.data[0].id);
                }
                rendercart(data);
                document.getElementById('total-price').innerHTML= 'Total price : '+data.data[0].price;
            })

    }else{

    };

}


getCartData(0);


function addcart(id) {
    const temp= sessionStorage.getItem('Id');
    if(temp==""  ){


        console.log("tao moi")

        fetch('/addNewCart'+`?id=${id}`,{method: 'Put'})
            .then(res => res.json())
            .then(data => {
                    console.log(data)
                }
            )
    }
    else {
        console.log("ok")
        fetch('/addCart'+`?cartId=${temp}&shoeId=${id}`,{method:'Put'})
            .then(res => res.json())
            .then(data => {
                console.log(data)

            })


    }

}


const duy1=document.querySelectorAll('.button-cart');
duy1.forEach(item=>{
    const id=item.getAttribute('data-id');
    item.addEventListener('click',()=>{
        addcart(id);

    })
})


const checkout=document.getElementById('checkoutbtn');
checkout.addEventListener('click',()=>{
    const id = sessionStorage.getItem('Id')
    fetch(`/purchaseCart?cartId=${id}`,{method:'Put'})
        .then(res=>res.json())
        .then(data=>{
            sessionStorage.setItem('Id',"");

        })

})

const reload=document.getElementById('resetbtn');
reload.addEventListener('click',()=>{
    location.reload();

})
















