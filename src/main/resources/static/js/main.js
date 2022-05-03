Vue.resource('/product/all{/id}');
Vue.component('product-row', {
    props: ['product'],
    template: '<div><i>({{ product.id }})</i> {{ product.text }}</div>'
});

Vue.component('products-list', {
    props: ['products'],
    template:
        `<div><product-row v-for="product in products" :key="product.id":product="product"></product-row></div>`
});
new Vue({
    el: '#app',
    template: '<products-list :products="products" />',
    data: {
        products: [
            {id:'123',text:'wow'}
        ]
    }
});
