var globalMarkers = [];

var Markers = {
    FOOD: {
        type: "picture-marker",
        url: "icons/food.png",
        width: "35px",
        height: "35px"
    },
    LIVABILITY: {
        type: "picture-marker",
        url: "icons/livability.png",
        width: "35px",
        height: "35px"
    },
    WASTE: {
        type: "picture-marker",
        url: "icons/waste.png",
        width: "35px",
        height: "35px"
    },
    WATER: {
        type: "picture-marker",
        url: "icons/water.png",
        width: "35px",
        height: "35px"
    },
    ENERGY: {
        type: "picture-marker",
        url: "icons/energy.png",
        width: "35px",
        height: "35px"
    },
    BUILDINGS: {
        type: "picture-marker",
        url: "icons/buildings.png",
        width: "35px",
        height: "35px"
    },
    TRANSPORTATION: {
        type: "picture-marker",
        url: "icons/transportation.png",
        width: "35px",
        height: "35px"
    },
    CLEANEVENT: {
        type: "picture-marker",
        url: "https://maps.google.com/mapfiles/ms/icons/green-dot.png",
        width: "35px",
        height: "35px"
    },
    GREENEVENT: {
        type: "picture-marker",
        url: "https://maps.google.com/mapfiles/ms/icons/yellow-dot.png",
        width: "35px",
        height: "35px"
    },
    FOODEVENT: {
        type: "picture-marker",
        url: "https://maps.google.com/mapfiles/ms/icons/red-dot.png",
        width: "35px",
        height: "35px"
    },
    LIVEVENT: {
        type: "picture-marker",
        url: "https://maps.google.com/mapfiles/ms/icons/blue-dot.png",
        width: "35px",
        height: "35px"
    },
    WASTEEVENT: {
        type: "picture-marker",
        url: "https://maps.google.com/mapfiles/ms/icons/blue-dot.png",
        width: "35px",
        height: "35px"
    },
    TRANSPORTATIONEVENT: {
        type: "picture-marker",
        url: "icons/lightblue-dot.png",
        width: "35px",
        height: "35px"
    }
};
//creates an enum of formatted custom markers 

function iconSelect(pillar) {
    // Set up value
    var value = 1;

    // Check which icon
    switch (pillar) {
        case "1":
            value = Markers.ENERGY;
            break;
        case "2":
            value = Markers.WATER;
            break;
        case "3":
            value = Markers.TRANSPORTATION;
            break;
        case "4":
            value = Markers.BUILDINGS;
            break;
        case "5":
            value = Markers.LIVABILITY;
            break;
        case "6":
            value = Markers.FOOD;
            break;
        case "7":
            value = Markers.WASTE;
            break;
        case "8":
            value = Markers.CLEANEVENT;
            break;
        case "9":
            value = Markers.GREENEVENT;
            break;
        case "10":
            value = Markers.FOODEVENT;
            break;
        case "11":
            value = Markers.LIVEVENT;
            break;
        case "12":
            value = Markers.WASTEEVENT;
            break;
        case "13":
            value = Markers.TRANSPORTATIONEVENT;
            break;
    }
    return value;
}
//selects the icons based on the corresponding pillar

require([
    "esri/config",
    "esri/Map",
    "esri/views/MapView",
    "esri/layers/FeatureLayer",
    "esri/Graphic",
    "esri/layers/GraphicsLayer",
    "esri/symbols/PictureMarkerSymbol",
    "esri/rest/locator"
],
    function (esriConfig, Map, MapView, FeatureLayer, Graphic, GraphicsLayer, PictureMarkerSymbol) {

        // Map Key
        esriConfig.apiKey = mapKey;
        const map = new Map({
            basemap: "arcgis-navigation" // Basemap layer service
        });

        // Custom Layer
        const layer = new FeatureLayer({
            url: "https://services2.arcgis.com/zjuHsgZRm0zT5o41/arcgis/rest/services/orlando_city/FeatureServer/0"
        });

        map.add(layer);
        // End Custom Layer

        const view = new MapView({
            map: map,
            //Orlando: -81.3789, 28.5384
            center: [-81.3789, 28.5384], // Longitude, latitude of Orlando, FL
            zoom: 9, // Zoom level
            container: "viewDiv" // Div element
        });

        //to add a GraphicsLayer instead of a FeatureLayer for marker population
        var gL = new GraphicsLayer();
        map.add(gL);// adds GraphicsLayer - fails to render marker

        var graphic;

        for (let i = 0; i < points.length; i++) {
            // Add graphic
            var tempPoint = addPoints(points[i]);

            // Check whats on the feature map
            queryFeatureLayerView(points[i], 0, "intersects", "", tempPoint);

        } //TODO change so the icon is dynamically selected based on the points provided

        // Set up pop up
        view.popup.autoOpenEnabled = false;

        // Get the screen point from the view's click event
        view.on("click", function (event) {
            var screenPoint = {
                x: event.x,
                y: event.y
            };

            // Search for graphics at the clicked location
            // https://community.esri.com/t5/arcgis-api-for-javascript-questions/esri-javascript-4x-graphicslayer-on-click-event/m-p/482838
            view.hitTest(screenPoint).then(function (response) {
                if (response.results.length) {
                    try {
                        var graphic = response.results.filter(function (result) {
                            // Check if the graphic belongs to the layer of interest
                            return result.graphic.layer === gL;
                        })[0].graphic;

                        // Pop up
                        view.popup.open({
                            // Set the popup's title to the coordinates of the location
                            title: graphic.attr.name,
                            location: event.mapPoint,
                            content: graphic.attr.content
                        });
                    }
                    catch
                    {
                        // Empty
                    }
                }
            });
        });

        // addPoints: Add a graphic point, return the index in array
        function addPoints(point) {
            //TODO add a distinguising method for each of the points and corresponding marker
            graphic = new Graphic({
                geometry: point,
                symbol: iconSelect(point['dbType']),
                attr: point
            });
            //add a popupTemplate property for the display content

            // Add to global
            globalMarkers.push(graphic);

            gL.add(graphic);

            return globalMarkers.length - 1;
        }

        // queryFeatureLayerView: Queries the feature layer to see what objects are in that area
        function queryFeatureLayerView(point, distance, spatialRelationship, sqlExpression, tempPoint) {
            // Add the layer if it is missing
            if (!map.findLayerById(layer.id)) {
                layer.outFields = ["*"];
                map.add(layer, 0);
            }
            // Set up the query
            var query = {
                geometry: point,
                distance: distance,
                spatialRelationship: spatialRelationship,
                outFields: ["*"],
                returnGeometry: true,
                where: sqlExpression
            };
            // Wait for the layerview to be ready and then query features
            view.whenLayerView(layer).then(function (featureLayerView) {
                if (featureLayerView.updating) {
                    var handle = featureLayerView.watch("updating", function (isUpdating) {
                        if (!isUpdating) {
                            // Execute the query
                            featureLayerView.queryFeatures(query).then(function (result) {
                                // If not on polygon, hide
                                if (result.features.length == 0) {
                                    globalMarkers[tempPoint].visible = false;
                                }
                            });
                            handle.remove();
                        }
                    });
                } else {
                    // Execute the query
                    featureLayerView.queryFeatures(query).then(function (result) {
                        // If not on polygon, hide
                        if (result.features.length == 0) {
                            globalMarkers[tempPoint].visible = false;
                        }
                    });
                }
            });
        }
    });