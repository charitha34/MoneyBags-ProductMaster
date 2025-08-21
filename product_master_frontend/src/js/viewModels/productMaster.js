define([
  'knockout',
  'ojs/ojtable',
  'ojs/ojmodel',
  'models/featureCollection',
  'ojs/ojcollectiondataprovider',
  'ojs/ojbutton',
  'ojs/ojformlayout',
  'ojs/ojinputtext',
  'ojs/ojdialog'
], function (ko, ojtable, ojmodel, ProductCollection, CollectionDataProvider) {

  function ProductsViewModel() {
    var self = this;

    // Collection and DataProvider
    self.collection = new ProductCollection();
    self.dataProvider = ko.observable();

    // Observables for product fields
    self.id = ko.observable();            // 🔹 Technical key
    self.productId = ko.observable();     // 🔹 Functional key
    self.productName = ko.observable();
    self.productType = ko.observable();
    self.interestRate = ko.observable();
    self.minBalance = ko.observable();
    self.accountOpenCharge = ko.observable();
    self.status = ko.observable();

    // Fetch products
    self.fetchProducts = function () {
      self.collection.fetch({
        success: function () {
          self.dataProvider(new CollectionDataProvider(self.collection));
          console.log("Products loaded");
        },
        error: function (jqXHR, textStatus) {
          console.error("Fetch error:", textStatus, jqXHR);
        }
      });
    };

    // Clear input fields
    self.clearFields = function () {
      self.id('');
      self.productId('');
      self.productName('');
      self.productType('');
      self.interestRate('');
      self.minBalance('');
      self.accountOpenCharge('');
      self.status('');
    };

    // Dialog openers
    self.openAddDialog = function () {
      self.clearFields();
      document.getElementById('addProductDialog').open();
    };

    self.openEditDialog = function () {
      document.getElementById('editProductDialog').open();
    };

    self.openDeleteDialog = function () {
      document.getElementById('deleteProductDialog').open();
    };

    // Add product (🔹 requires technical key id)
    self.addProduct = function () {
      if (!self.id() || !self.productId() || !self.productName() || !self.productType() || !self.status()) {
        alert("Please fill ID, Product ID, Name, Type, and Status before submitting.");
        return;
      }

      var newProduct = {
        id: parseInt(self.id()),   // technical key
        productId: parseInt(self.productId()), // functional key
        productName: self.productName(),
        productType: self.productType(),
        interestRate: self.interestRate() ? parseFloat(self.interestRate()) : 0,
        minBalance: self.minBalance() ? parseFloat(self.minBalance()) : 0,
        accountOpenCharge: self.accountOpenCharge() ? parseFloat(self.accountOpenCharge()) : 0,
        status: self.status()
      };

      console.log("Sending payload:", newProduct);

      self.collection.create(
        newProduct,
        {
          wait: true,
          contentType: "application/json",
          success: function () {
            alert("Product added successfully.");
            document.getElementById("addProductDialog").close();
            self.fetchProducts();
          },
          error: function (model, xhr, options) {
            alert("Failed to add product.\nStatus: " + xhr.status + "\nResponse: " + xhr.responseText);
            console.error("Add product error:", xhr);
          }
        }
      );
    };

    // Edit product (🔹 update using id)
self.editProduct = function () {
  var product = self.collection.get(self.id()); // lookup by technical key
  if (product) {
    product.set({
      productId: parseInt(self.productId()),
      productName: self.productName(),
      productType: self.productType(),
      interestRate: parseFloat(self.interestRate()),
      minBalance: parseFloat(self.minBalance()),
      accountOpenCharge: parseFloat(self.accountOpenCharge()),
      status: self.status()
    });
    product.save(null, {
      wait: true,
      success: function () {
        alert("Product updated successfully.");
        document.getElementById("editProductDialog").close();
        self.fetchProducts();
      },
      error: function (model, error) {
        alert("Failed to update product.");
        console.error("Update error:", error);
      }
    });
  } else {
    alert("Product not found.");
  }
};

    
    // Delete product (🔹 delete using id)
    self.deleteProduct = function () {
      var product = self.collection.get(self.id());
      if (product) {
        product.destroy({
          wait: true,
          success: function () {
            alert("Product deleted successfully.");
            document.getElementById("deleteProductDialog").close();
            self.fetchProducts();
          },
          error: function (model, error) {
            alert("Failed to delete product.");
            console.error("Delete error:", error);
          }
        });
      } else {
        alert("Product not found.");
      }
    };

    // Initial load
    self.fetchProducts();
  }

  return new ProductsViewModel();
});
