<%@ page import="com.GREENWORKS.eco.EcoMap" %>
<%
EcoMap e = new EcoMap();
%>

<html>
    <head>
        <!--
            Filename: index.jsp
            Date of Creation: 27 Sep 2021
            Editor: Kashai Bingham
            Edited: 29 Nov 2021
            Version: 2.5.5
        -->
        
        <meta charset="utf-8" />
        <title>Eco-Map - City of Orlando</title>
        <link href="stylesheets/ecomap_stylesheet.css" rel="stylesheet" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="cred.js"></script>
    </head>

    <body>
        <script>
        // Load array in from Java
        var array = [<% for (int i = 0; i < e.getLocations().size(); i++) { %>"<%= e.getLocations().get(i) %>"<%= i + 1 < e.getLocations().size() ? ",":"" %><% } %>];
        
        // Create set up variables
        var markers = [];
        var tempArray = [];

        // Array iteration count
        var j = 0;

        // Loop through Java array
        for(i = 0; i <= array.length - 1; i++)
        {
            // Push values to temporary array
            tempArray.push(array[i]);

            // Increment iteration count
            j++;

            // If on the fourth value (final)
            if(j == 5)
            {
                // Push to markers array
                markers.push(tempArray);

                // Clear temp array
                tempArray = [];

                // Reset iteration count
                j = 0;
            }
        }
        </script>

        <header>
            <a id="menu" href="">&#9776; Menu</a>
            <a id="logo" href="https://www.orlando.gov"><img src="icons/CityOfOrlando_logo.png" alt="City of Orlando logo"></a>
            <input id="search" type="text" placeholder="Find almost anything on our website">
            <input id="search_button" type="button" value="Search">
            <a id="mobile_search" href=""><img src="icons/search_icon.png"></a>
        </header>
        <!-- adds the mock header for the page including the CoO logo, a search field, and a menu -->

        <section>
            <div>
                <ul class="breadcrumb">
                    <li><a href="https://www.orlando.gov/Home">Home</a></li>
                    <li>Eco-Map</li>
                </ul>
            </div>
            <!-- adds a static breadcrumb navigation to show the user the URL path taken to the current page -->
            <h1>Eco-Map</h1>
            <div class="map_options">
                <div id="filter_items">
                    <figure>
                        <a href="#/" name="openFilter">
                            <img id="filter_icon" src="icons/filter.png" alt=" ">
                            <div id="fc_background"><figcaption>Filter</figcaption></div>
                        </a>
                    </figure>
                </div>
                <!--solution found on StackOverflow: https://stackoverflow.com/questions/5003867/how-to-call-javascript-function-instead-of-href-in-html/5003904-->

                <div id="calendar_items">
                    <figure>
                        <a href="javascript:void(0);" >
                            <img id="calendar_icon" src="icons/calendar.png" alt=" ">
                            <div id="fc_background"><figcaption>Events</figcaption></div>
                        </a>
                    </figure>
                </div>
            </div>
        </section>

        <div id="filter">
                <form action="showFilter" name="filter" method="post" accept-charset="utf-8">
                    <h2>Filter</h2><hr>
                    <div class="pillars">
                        <div class="options">
                            <input class="check" type="checkbox" name="pillar_name" value="1" CHECKED></input><label class="label">Clean Energy</label>
                        </div>
                        <div class="options">
                            <input class="check" type="checkbox" name="pillar_name" value="2" CHECKED></input><label class="label">Clean Water</label>
                        </div>
                        <div class="options">
                            <input class="check" type="checkbox" name="pillar_name" value="3" CHECKED></input><label class="label">Electic & Alt. Transportation</label>
                        </div>
                        <div class="options">
                            <input class="check" type="checkbox" name="pillar_name" value="4" CHECKED></input><label class="label">Green Buildings</label>
                        </div>
                        <div class="options">
                            <input class="check" type="checkbox" name="pillar_name" value="5" CHECKED></input><label class="label">Livability</label>
                        </div>
                        <div class="options">
                            <input class="check" type="checkbox" name="pillar_name" value="6" CHECKED></input><label class="label">Local Food Systems</label>
                        </div>
                        <div class="options">
                            <input class="check" type="checkbox" name="pillar_name" value="7" CHECKED></input><label class="label">Zero Waste</label>
                        </div>
                    </div>
                    <hr>
                    <div class="action_controls">
                        <button name="closeFilter">Close</button>
                    </div>
                </form>
        </div>
        
        <div id="map"></div> 
        <!--sets the parameters for the Google Maps API embed -->
 
        <script>
            document.write('<script async defer src="https://maps.googleapis.com/maps/api/js?key=' + googleMapKey + '&callback=initMap"><\/script>');
        </script>
        
        <script src="scripts/mapEmbed.js"></script> 
        <!--references the init callback to the mapEmbed js file -->       
        
        <div>
            <footer>
                <div id="left">
                  <a href="https://www.orlando.gov/System-pages/Website-Legal-Notices">Website Legal Notice</a>  | 
                  <a href="https://www.orlando.gov/General-Pages/Sitemap">Sitemap</a> | 
                  <a href="https://www.orlando.gov/Our-Government/News-and-Information/City-Official-Assets">The City Beautiful</a>
                </div>
                <div id="right">
                 &copy; 2021 City of Orlando | 
                Powered by <a href="http://www.opencities.com/">OpenCities</a>
                </div>                           
              </footer>
        </div>
        <!-- simulates the footer as shown on the real CoO site -->
        <script src="scripts/main.js"></script>
    </body>
</html>