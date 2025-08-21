define(['ojs/ojmodel', 'models/featureModel'], function (oj, FeatureModel) {
    var FeatureCollection = oj.Collection.extend({
        url: 'http://localhost:5050/products',  
        model: FeatureModel
    });
    return FeatureCollection;
});

