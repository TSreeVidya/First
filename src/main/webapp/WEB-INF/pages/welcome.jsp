<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>Accenture - Bookstore</title>
<link
	href="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/css/bootstrap-combined.min.css"
	rel="stylesheet" />
</head>
<body>

	<div class="container" style="width: 80%">
		<h1>Welcome to the Accenture bookstore</h1>

		<h2>Available books:</h2>
			
			<div class="well">
    <table class="table" id="book-list">
      <thead>
      <tr>
        <th></th>
        <th>Book Title</th>
        <th style="text-align: right">Price</th>
        <th>&nbsp;</th>
      </tr>
      </thead>

      <tbody></tbody>
    </table>
  </div>

  <hr/>

  <div>
    <h5>Shopping cart</h5>

    <div style="width:40%" class="well">
      <span id="empty-cart"><em>Cart is empty</em></span>
      <table class="table table-condensed" id="cart">
        <thead>
        <tr>
          <th>Item</th>
          <th style="text-align: right">Quantity</th>
          <th style="text-align: right">Price</th>
        </tr>
        </thead>
        <tbody></tbody>
      </table>
    </div>
    <div>
      <button id="btn-clear-cart" class="btn btn-info">Clear cart</button>
    </div>

  </div>

  <hr/>

  <div id="order-info">
    <form class="form-horizontal">
      <fieldset>

        <div id="order-result-message"></div>
        <!-- Address form -->
        <h2>Order information</h2>

        <!-- full-name input-->
        <div class="control-group">
          <label for="customerName" class="control-label">Full Name</label>

          <div class="controls">
            <input id="customerName" name="full-name" type="text" placeholder="John Doe"
                   class="input-xlarge"/>
          </div>
        </div>

        <!-- address-->
        <div class="control-group">
          <label for="customerAddress" class="control-label">Address</label>

          <div class="controls">
            <input id="customerAddress" name="address" type="text" placeholder="Highway street 1"
                   class="input-xlarge"/>
          </div>
        </div>

        <!-- email input-->
        <div class="control-group">
          <label for="customerEmail" class="control-label">Email</label>

          <div class="controls">
            <input id="customerEmail" name="email" type="email" placeholder="john.doe@acme.com"
                   class="input-xlarge"/>
          </div>
        </div>

        <!-- Submit -->
        <div class="control-group">
          <div class="controls">
            <button id="btn-place-order" class="btn btn-success">Place order</button>
          </div>
        </div>

      </fieldset>
    </form>
  </div>
  
		<!-- Latest compiled and minified JavaScript -->

		<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
		<script
			src="http://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>
		<script
			src="http://cdnjs.cloudflare.com/ajax/libs/jquery-cookie/1.4.0/jquery.cookie.min.js"></script>

		<!-- Load all books -->
		<script language="javascript">
			//var shoppingService = "/service/carts/";
			var shoppingService = "/RapidTest/bookstore/";
			var productCatalogService = "http://localhost:8090/products";
			var orderService = "http://localhost:8070/service/order-requests";
			var xmlDocument;
			/*
			$(document).ready(function () {
				  loadAndRenderProducts();
				  alert('after rendering');
				  
				  if (!hasCartIdCookie()) {
					  alert('in if');
				    initCart(loadCart);
				  } else {
					  alert('in else');
				    loadCart();
				  }
				});
			
			*/
			$(document).ready(function() { // load xml file using jquery ajax
				$.ajax({
					type : "GET",
					url : "../xml/products.xml", //url: "data.xml",
					dataType : "xml",
					success : function(xml) {
						renderProducts(xml);
					}
				});
			});

			$(document).on("click", "#book-list button", function() {
				var buttonId = this.id;
				var productId = buttonId.replace("#add-item-", "");
				//alert('productId - '+productId);
				//var url = shoppingService + getCartIdFromCookie() + "/addBook";
				var url = shoppingService+ "addBook";
				alert('add click url - '+url);
				
				//var payload ="<productId>"+productId+"</productId>";
				
var payload ="<bkr:AddBookRequestMessage xmlns:bkr=\"http://www.consol.com/schemas/bookstore\">"+
"<bkr:book>"+
"<bkr:title>Maven: The Definitive Guide</bkr:title>"+
"<bkr:author>Mike Loukides, Sonatype</bkr:author>"+
"<bkr:isbn>"+guid()+"</bkr:isbn>"+ //978-0596517359
"<bkr:year>2008</bkr:year>"+
"</bkr:book>"+
"</bkr:AddBookRequestMessage>";

				postXML(url, payload).error(function(data) {
					alert('error >>>> '+data);
				alert('error'+ data.status);
				}).done(loadCart);
				
				
				/*
				var payload = JSON.stringify({
					"productId" : productId
				});
				postJSON(url, payload).error(function(data) {
					alert("Error: " + data);
				}).done(loadCart);
				*/
			});

			$('#btn-place-order').click(function() {
				placeOrder();
				return false;
			});

			$('#btn-clear-cart').click(function() {
				deleteCart(function(result) {
					initCart(loadCart);
				});
				return false;
			});

			function hasCartIdCookie() {
				return !(getCartIdFromCookie() === undefined);
			}

			function getCartIdFromCookie() {
				return $.cookie('cart');
			}

			function deleteCart(onSuccess) {
				var cartId = getCartIdFromCookie();
				console.log("Deleting session: " + cartId);
				$.ajax({
					url : shoppingService + cartId,
					type : 'DELETE',
					success : onSuccess
				});
			}

			function initCart(onDone) {
				alert('initCart');
				$.removeCookie("cart");
				var newCartId = guid();
				alert('newCartId'+newCartId);
				
			//	display new book information here +++++ 
				postJSON(shoppingService, JSON.stringify({
					"cartId" : newCartId
				}))
						.error(function(data) {
							alert("Error: " + data);
						})
						.done(
								function() {
									console
											.log("Successfully initialized new session: "
													+ newCartId);
									$.cookie('cart', newCartId);
									onDone();
								});
			}

			function loadCart(cart) {
				alert('loadCart');
				alert('cart - '+cart);
				renderCart(cart);
						
			}

			function renderCart(cart) {
				var cartSelector = $('#cart');
				cartSelector.find("tbody tr").remove();

				if (cart.length === null) {
					cartSelector.hide();
					$('#empty-cart').show();
				} else {
					$('#empty-cart').hide();
					renderCartItems(cart);
				}
			}

			function renderCartItems(cart) {
				alert('renderCartItems');
				var cartSelector = $('#cart');
				
				$(cart)
				.find('product')
				.each(
						function() {
							var productId = $(this).find('productId')
									.text();
							var price = $(this).find('price').text();
							var itemId = 'add-item-' + productId;
							var bookObj = $(this).find('book');
							var isbn = $(bookObj).find('isbn').text();
							var title = $(bookObj).find('title').text();
							var qty=1;
							cartSelector.find("tbody").append(
									'<tr><td>' + title + '</td>'
											+ '<td><p class="text-right">' + qty
											+ '</p></td>'
											+ '<td><p class="text-right">'
											+isbn
											//+ toDollarString(totalPrice)
											+ '</p></td></tr>');
							cartSelector.show();
							
						});
				
				
				cartSelector.find("tbody tr:last").after(
						'<tr><td><b>Totals:</b></td>'
								+ '<td><p class="text-right"><b>'
								+ cart.totalQuantity + '</b></p></td>'
								+ '<td><p class="text-right"><b>'
								+ toDollarString(cart.totalPrice)
								+ '</p></b></td></tr>');

			}

			function toDollarString(moneyInCents) {
				return "$ " + (moneyInCents / 100).toFixed(2);
			}

			function loadAndRenderProducts() {
				/*$.getJSON(productCatalogService, function(products) {
					renderProducts(products);
				});
				*/
				$.ajax({
					type : "GET",
					url : "../xml/products.xml", //url: "data.xml",
					dataType : "xml",
					success : function(xml) {
						renderProducts(xml);
					}
				});
				
			}

			function renderProducts(products) {
				xmlDocument = products;
				//var list = products == null ? [] : (products instanceof Array ? products : [products]);
				var bookListSelector = $('#book-list');
				bookListSelector.find('tbody').remove();
				$(products)
						.find('product')
						.each(
								function() {
									var productId = $(this).find('productId')
											.text();
									var price = $(this).find('price').text();
									var itemId = 'add-item-' + productId;
									var bookObj = $(this).find('book');
									var isbn = $(bookObj).find('isbn').text();
									var title = $(bookObj).find('title').text();
									//alert('title - '+title);
									bookListSelector.append(
									        '<tr><td><img id=' + isbn + ' height="80px" width="65px"/></td>' +
									            '<td>' + title + '</td>' +
									            '<td><p class="text-right">' + toDollarString(price) + '</p></td>' +
									            '<td><button class="btn-small btn-success" id="#' + itemId + '">Add</button></td></tr>');
									
								});

				$(products)
						.find('product').each(
							function(index) {
							var isbnForImage = $(this).find('book').find('isbn').text();
							//alert('isbnForImage - '+isbnForImage);
							getCoverImageUrl(isbnForImage, function (imageUrl) {
							//alert('imageUrl  - '+imageUrl);
								$('#' + isbnForImage).attr("src", imageUrl);
					});
								
				});
					
				
			}

			function getCoverImageUrl(isbn, callback) {
			//alert('getCoverImageUrl isbn - '+isbn);
				$
						.ajax({
							url : 'https://www.googleapis.com/books/v1/volumes?q=isbn:'
									+ isbn,
							dataType : 'json',
							success : function(data) {
								if (data.items != undefined) {
									callback(data.items[0].volumeInfo.imageLinks.smallThumbnail);
								}
							},
							error : function(data) {
								console.log('Got error: ' + data.error.message
										+ ' when getting ' + isbn);
								callback("book.png");
							}
						});
			}

			function placeOrder() {
				console.log("Placing order...");
				alert('placeOrder');
				var sessionId = getCartIdFromCookie();
				var message = '<div class="alert alert-success">'
					+ '<span>Order was successfully placed!</span>'
					+ '</div>';
			$(
					"#order-result-message")
					.html(message)
					.show().delay(
							3000)
					.fadeOut();
			var cartSelector = $('#cart');
			cartSelector.find("tbody tr").remove();
						
			
				/*
				$
						.getJSON(
								shoppingService + sessionId,
								function(cart) {
									console.log("Reading contents in session: "
											+ JSON.stringify(cart));
									postJSON(orderService,
											createOrderRequestJson(cart))
											.error(
													function(data) {
														var message = '<div class="alert alert-error">'
																+ '<span>There was an error processing your order</span>'
																+ '</div>';
														$(
																"#order-result-message")
																.html(message)
																.show().delay(
																		3000)
																.fadeOut();
													})
											.done(
													function(data) {
														var message = '<div class="alert alert-success">'
																+ '<span>Order was successfully placed!</span>'
																+ '</div>';
														$(
																"#order-result-message")
																.html(message)
																.show().delay(
																		3000)
																.fadeOut();
														deleteCart(function() {
															initCart(loadCart);
														});
														$("#order-info input")
																.val("");
													});
								});
				*/
			}

			function createOrderRequestJson(cart) {
				console.log("Sending cart: " + JSON.stringify(cart));
				return JSON.stringify({
					"cart" : cart,
					"orderId" : guid(),
					"customerName" : $('#customerName').val(),
					"customerEmail" : $('#customerEmail').val(),
					"customerAddress" : $('#customerAddress').val()
				});
			}

			function postJSON(url, data, callback) {
				//alert(url);
				return $.ajax({
					'type' : 'POST',
					'url' : url,
					'contentType' : 'application/json',
					'data' : data,
					'dataType' : 'json',
					'success' : callback
				});
			}

			function postXML(url, data, callback) {
				alert('postXML'+data);
				
				return $.ajax({
					'type' : 'POST',
					'url' : url,
					'contentType' : 'text/xml', //'application/xml',
					'data' : data //,
				//	'dataType' : 'xml',
				//	'success' : callback
				});
				/*
				$.ajax({
					url : url,
					type : "POST",
					data : data,
					contentType : 'text/xml', 
					//dataType : 'xml',
					success: function(result) {		
						if (result.indexOf("Failure") > -1) {
							alert("Get Book details failed");
						} else {
							alert("Got book details successfully");
						}										
					},				
					error: function(result) {
						alert("failure. "+result);
					}
				}); 
				*/
			}
						
			/**
			 * Generates a GUID string.
			 * @returns {String} The generated GUID.
			 * @example af8a8416-6e18-a307-bd9c-f2c947bbb3aa
			 * @author Slavik Meltser (slavik@meltser.info).
			 * @link http://slavik.meltser.info/?p=142
			 */
			function guid() {
				function _p8(s) {
					var p = (Math.random().toString(16) + "000000000").substr(
							2, 8);
					return s ? "-" + p.substr(0, 4) + "-" + p.substr(4, 4) : p;
				}

				return _p8() + _p8(true) + _p8(true) + _p8();
			}
		</script>
</body>
</html>