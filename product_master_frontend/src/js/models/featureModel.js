
define(['ojs/ojmodel'], function (oj) {
    var FeatureModel = oj.Model.extend({
        idAttribute: 'id',  // Use the technical key (surrogate PK) from your table
        urlRoot: 'http://localhost:5050/products'
    });
    return FeatureModel;
});
