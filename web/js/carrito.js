/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function(){
   $('tr #deleteitem').click(function(e){
       e.preventDefault();
//       alert();
      var elemento = $(this);
      var idproducto = elemento.parent().find('#idarticulo').text();
      $.ajax({
         url: 'eliminar_producto',
         type: 'post',
         data : {idProducto : idproducto},
         success: function(resultado){
             elemento.parent().parent().remove();
             //cart-container
             var elementosTabla = $('#shop-table tr');
             if(elementosTabla.length <= 1){
                 $('#cart-container').append("<h4>No hay articulos en el carro de compras</h4>");
             }
             $('#txt-subtotal').text(resultado);
             $('#txt-total').text(resultado);
         }
      });
   });
//   var elementosTabla = $('#shop-table tr');
//   alert(elementosTabla.length);
   
});
