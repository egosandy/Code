package com.rcdriver.dr.gmap;

import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.rcdriver.dr.activity.MainActivity;
import com.rcdriver.dr.utils.Log;

/*
 * =================================================================================================
 * PENTING: KELAS INI MELAKUKAN PROSES JARINGAN (NETWORK).
 * ANDA HARUS MEMANGGIL SEMUA METODE DARI KELAS INI DARI BACKGROUND THREAD, BUKAN DARI MAIN THREAD.
 * JIKA TIDAK, APLIKASI AKAN CRASH DENGAN ERROR NetworkOnMainThreadException.
 *
 * Contoh Penggunaan yang Benar:
 *
 * new Thread(() -> {
 * GMapDirection gMap = new GMapDirection();
 * Document doc = gMap.getDocument(startLatLng, endLatLng, GMapDirection.MODE_DRIVING);
 *
 * // Untuk update UI, kembali ke Main Thread
 * runOnUiThread(() -> {
 * if (doc != null) {
 * String duration = gMap.getDurationText(doc);
 * textViewDurasi.setText(duration);
 * } else {
 * Toast.makeText(getApplicationContext(), "Gagal mendapatkan rute", Toast.LENGTH_SHORT).show();
 * }
 * });
 * }).start();
 *
 * =================================================================================================
 */
public class GMapDirection {
    public final static String MODE_DRIVING = "driving";
    public final static String MODE_WALKING = "walking";

    public GMapDirection() {
    }

    // Metode getUrl dan getUrlVia sudah meminta JSON, jadi biarkan saja jika Anda menggunakannya
    // dengan library lain seperti Retrofit/Volley yang mem-parsing JSON.
    // Jika Anda menggunakan metode getDocument di bawah, URL ini tidak relevan.
    public String getUrl(LatLng start, LatLng end, String mode, boolean isAlternative) {
        String url = "https://maps.googleapis.com/maps/api/directions/json?"
                + "origin=" + start.latitude + "," + start.longitude
                + "&destination=" + end.latitude + "," + end.longitude
                + "&sensor=false&units=metric&mode=" + mode
                + "&key=" + MainActivity.apikey;

        if (isAlternative)
            url += "&alternatives=true";

        Log.e("getUrl", url);
        return url;
    }

    public Document getDocument(LatLng start, LatLng end, String mode) {
        // PERBAIKAN: Mengganti output dari "json" ke "xml" agar sesuai dengan parser
        String url = "https://maps.googleapis.com/maps/api/directions/xml?"
                + "origin=" + start.latitude + "," + start.longitude
                + "&destination=" + end.latitude + "," + end.longitude
                + "&sensor=false&units=metric&mode=" + mode
                + "&key=" + MainActivity.apikey;

        url = url.replace(" ", "+");
        Log.e("URL", url);

        try {
            URL ur = new URL(url);
            URLConnection connection = ur.openConnection();

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(connection.getInputStream());

            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Document getDocument(String origin, String destination, String mode) {
        // PERBAIKAN: Mengganti output dari "json" ke "xml" agar sesuai dengan parser
        String url = "https://maps.googleapis.com/maps/api/directions/xml?"
                + "origin=" + origin + "&destination=" + destination
                + "&sensor=false&units=metric&mode=" + mode
                + "&key=" + MainActivity.apikey;

        url = url.replace(" ", "+");
        Log.e("Query URL", url);

        try {
            URL ur = new URL(url);
            URLConnection connection = ur.openConnection();

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(connection.getInputStream());
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDurationText(Document doc) {
        try {
            NodeList nl1 = doc.getElementsByTagName("duration");
            Node node1 = nl1.item(0);
            NodeList nl2 = node1.getChildNodes();
            Node node2 = nl2.item(getNodeIndex(nl2, "text"));
            Log.i("DurationText", node2.getTextContent());
            return node2.getTextContent();
        } catch (Exception e) {
            return "N/A";
        }
    }

    public int getDurationValue(Document doc) {
        try {
            NodeList nl1 = doc.getElementsByTagName("duration");
            Node node1 = nl1.item(0);
            NodeList nl2 = node1.getChildNodes();
            Node node2 = nl2.item(getNodeIndex(nl2, "value"));
            Log.i("DurationValue", node2.getTextContent());
            return Integer.parseInt(node2.getTextContent());
        } catch (Exception e) {
            return 0;
        }
    }

    public String getDistanceText(Document doc) {
        try {
            NodeList nl1 = doc.getElementsByTagName("distance");
            Node node1 = nl1.item(0);
            NodeList nl2 = node1.getChildNodes();
            Node node2 = nl2.item(getNodeIndex(nl2, "text"));
            Log.i("DistanceText", node2.getTextContent());
            return node2.getTextContent();
        } catch (Exception e) {
            return "N/A";
        }
    }

    public int getDistanceValue(Document doc) {
        try {
            NodeList nl1 = doc.getElementsByTagName("distance");
            Node node1 = nl1.item(0);
            NodeList nl2 = node1.getChildNodes();
            Node node2 = nl2.item(getNodeIndex(nl2, "value"));
            Log.i("DistanceValue", node2.getTextContent());
            return Integer.parseInt(node2.getTextContent());
        } catch (Exception e) {
            return 0;
        }
    }

    public String getStartAddress(Document doc) {
        try {
            NodeList nl1 = doc.getElementsByTagName("start_address");
            Node node1 = nl1.item(0);
            Log.i("StartAddress", node1.getTextContent());
            return node1.getTextContent();
        } catch (Exception e) {
            return "N/A";
        }
    }

    public String getEndAddress(Document doc) {
        try {
            NodeList nl1 = doc.getElementsByTagName("end_address");
            Node node1 = nl1.item(0);
            Log.i("EndAddress", node1.getTextContent());
            return node1.getTextContent();
        } catch (Exception e) {
            return "N/A";
        }
    }

    public ArrayList<LatLng> getDirection(Document doc) {
        NodeList nl1, nl2, nl3;
        ArrayList<LatLng> listGeopoints = new ArrayList<>();
        nl1 = doc.getElementsByTagName("step");
        if (nl1.getLength() > 0) {
            for (int i = 0; i < nl1.getLength(); i++) {
                Node node1 = nl1.item(i);
                nl2 = node1.getChildNodes();

                Node locationNode = nl2.item(getNodeIndex(nl2, "start_location"));
                nl3 = locationNode.getChildNodes();
                Node latNode = nl3.item(getNodeIndex(nl3, "lat"));
                double lat = Double.parseDouble(latNode.getTextContent());
                Node lngNode = nl3.item(getNodeIndex(nl3, "lng"));
                double lng = Double.parseDouble(lngNode.getTextContent());
                listGeopoints.add(new LatLng(lat, lng));

                locationNode = nl2.item(getNodeIndex(nl2, "polyline"));
                nl3 = locationNode.getChildNodes();
                latNode = nl3.item(getNodeIndex(nl3, "points"));
                ArrayList<LatLng> arr = decodePoly(latNode.getTextContent());
                listGeopoints.addAll(arr);

                locationNode = nl2.item(getNodeIndex(nl2, "end_location"));
                nl3 = locationNode.getChildNodes();
                latNode = nl3.item(getNodeIndex(nl3, "lat"));
                lat = Double.parseDouble(latNode.getTextContent());
                lngNode = nl3.item(getNodeIndex(nl3, "lng"));
                lng = Double.parseDouble(lngNode.getTextContent());
                listGeopoints.add(new LatLng(lat, lng));
            }
        }
        return listGeopoints;
    }

    private int getNodeIndex(NodeList nl, String nodename) {
        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i).getNodeName().equals(nodename))
                return i;
        }
        return -1;
    }

    private ArrayList<LatLng> decodePoly(String encoded) {
        ArrayList<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng position = new LatLng((double) lat / 1E5, (double) lng / 1E5);
            poly.add(position);
        }
        return poly;
    }

    public String getUrlVia(String modeDriving, boolean b, LatLng pickUp, LatLng[] destination) {
        return null;
    }
}