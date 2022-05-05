function getIndex(list, id){
    for (var i=0; i < list.length; i++){
        if (list[i].id===id){
            return
        }
    }
    return -1;
}

var productApi = Vue.resource('/product');

Vue.component('product-form', {
    props:['products', 'productAttr'],
    data:function (){
        return{
            text:'',
            id:''
        }
    },
    watch:{
        productAttr: function(newVal, oldVal){
            this.text = newVal.text;
            this.id = newVal.id;
        }
    },
    template:
        '<div>' +
            '<input type="text"placeholder="Write something" v-model="text"/>'+
            '<input type="button" value="Save" @click="save"/>'+
        '</div>',
    methods:{
        save:function (){
            var product = { text: this.text };

            if (this.id){
                fetch('http://localhost:8080/product/' + this.id, {
                    method: 'PUT',
                    body: JSON.stringify(product),
                    headers: {
                        'Accept': 'application/json',
                        'Content-Type': 'application/json'
                    }
                }).then(result =>
                    result.json().then(data => {
                        var index = getIndex(this.products, data.id);
                        this.products.splice(index, 1, data);
                    })
                )
            } else {
                productApi.save({}, product).then(result =>
                    result.json().then(data => {
                        this.products.push(data);
                        this.text = ' '
                        tsis.id= ' '
                    })
                )
            }
        }
    }
});
Vue.component('product-row', {
    props: ['product', 'editProduct', 'products'],
    template: '<div><span><input type="button" value="X"@click="del"> <input type="button" value="Edit"@click="edit" /></span><i>({{ product.id }})</i> {{ product.text }}</div>',
    methods: {
        edit: function (){
            this.editProduct(this.product);
        },
        del: function (){
            fetch('http://localhost:8080/product/' + this.product.id, {
                method: 'DELETE',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                }
            }).then(result => {
                    if (result.ok){
                        this.products.splice(this.products.indexOf(this.product), 1)
                    }
                }
            )
        }
    }
});

Vue.component('products-list',
    {
        props: ['products'],
        data: function () {
            return {
                product: null
            }
        },//productAttr="product"  :editProduct="editProduct"
        template: '<div><product-form :productAttr="product" :products="products" /><product-row v-for="product in products" :key="product.id" :product="product" :editProduct="editProduct" :products="products"/></div>',

        created: function () {
            productApi.get().then(result => {
                this.products = result.data
                    console.log(result.data)
            }
            )
        },
        methods: {
            editProduct:function (product){
                this.product=product;
            }
        }
    });
var app = new Vue({
    el: '#app',
    template: '<products-list :products="products" />',
    data: {
        products: []
    }
});
