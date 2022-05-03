var productApi = Vue.resource('/product');

Vue.component('product-row', {
    props: ['product'],
    template: '<div><i>({{ product.id }})</i> {{ product.text }}</div>'
});

Vue.component('products-list',
    {
        props: ['products'],
        template:
            `<div><product-row v-for="product in products" :key="product.id":product="product"/></div>`,
        created: function () {
            productApi.get().then(result => {
                this.products = result.data
                    console.log(result.data)
            }

            )
        }
    });
var app = new Vue({
    el: '#app',
    template: '<products-list :products="products" />',
    data: {
        products: []
    }
});
