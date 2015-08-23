package org.allpossible.simpleweather;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ParseException;
import android.preference.PreferenceManager;
import android.text.format.DateFormat;

public class AppResource {

    private static ArrayList<Integer> day_icon = new ArrayList<Integer>();
    private static ArrayList<Integer> night_icon = new ArrayList<Integer>();
    private static ArrayList<Integer> weather_id = new ArrayList<Integer>();
    private static ArrayList<String> res_string = new ArrayList<String>();
    private static ArrayList<Integer> res_integer = new ArrayList<Integer>();
    private static HashMap<String, String> country_code = new HashMap<String, String>();
    public static HashMap<Integer, Integer> weather_desc = new HashMap<Integer, Integer>();
    private static AppResource res;
    SharedPreferences pref;

    private AppResource() {

    }

    public static AppResource getInstance() {
        if (res == null) {
            res = new AppResource();
        }
        intializeResources();
        return res;
    }

    private static void intializeResources() {
        // TODO Auto-generated method stub

        day_icon.add(R.drawable.ic_u);
        day_icon.add(R.drawable.ic_u);
        day_icon.add(R.drawable.ic_u);
        day_icon.add(R.drawable.ic_c);
        day_icon.add(R.drawable.ic_c);
        day_icon.add(R.drawable.ic_v);
        day_icon.add(R.drawable.ic_v);
        day_icon.add(R.drawable.ic_c);
        day_icon.add(R.drawable.ic_u);
        day_icon.add(R.drawable.ic_u);
        day_icon.add(R.drawable.ic_m);
        day_icon.add(R.drawable.ic_m);
        day_icon.add(R.drawable.ic_x);
        day_icon.add(R.drawable.ic_y);
        day_icon.add(R.drawable.ic_p);
        day_icon.add(R.drawable.ic_o);
        day_icon.add(R.drawable.ic_o);
        day_icon.add(R.drawable.ic_o);
        day_icon.add(R.drawable.ic_o);
        day_icon.add(R.drawable.ic_m);
        day_icon.add(R.drawable.ic_m);
        day_icon.add(R.drawable.ic_o);
        day_icon.add(R.drawable.ic_o);
        day_icon.add(R.drawable.ic_o);
        day_icon.add(R.drawable.ic_b);
        day_icon.add(R.drawable.ic_p);
        day_icon.add(R.drawable.ic_p);
        day_icon.add(R.drawable.ic_o);
        day_icon.add(R.drawable.ic_o);
        day_icon.add(R.drawable.ic_q);
        day_icon.add(R.drawable.ic_h);
        day_icon.add(R.drawable.ic_r);
        day_icon.add(R.drawable.ic_l);
        day_icon.add(R.drawable.ic_l);
        day_icon.add(R.drawable.ic_ab);
        day_icon.add(R.drawable.ic_l);
        day_icon.add(R.drawable.ic_ab);
        day_icon.add(R.drawable.ic_ab);
        day_icon.add(R.drawable.ic_a);
        day_icon.add(R.drawable.ic_af);
        day_icon.add(R.drawable.ic_j);
        day_icon.add(R.drawable.ic_af);
        day_icon.add(R.drawable.ic_j);
        day_icon.add(R.drawable.ic_j);
        day_icon.add(R.drawable.ic_j);
        day_icon.add(R.drawable.ic_j);
        day_icon.add(R.drawable.ic_ggg);
        day_icon.add(R.drawable.ic_af);
        day_icon.add(R.drawable.ic_ad);
        day_icon.add(R.drawable.ic_t);
        day_icon.add(R.drawable.ic_d);
        day_icon.add(R.drawable.ic_d);
        day_icon.add(R.drawable.ic_d);
        day_icon.add(R.drawable.ic_e);
        day_icon.add(R.drawable.ic_ad);
        day_icon.add(R.drawable.ic_ac);
        day_icon.add(R.drawable.ic_ad);
        day_icon.add(R.drawable.ic_l);
        day_icon.add(R.drawable.ic_k);
        day_icon.add(R.drawable.ic_w);
        day_icon.add(R.drawable.ic_d);
        day_icon.add(R.drawable.ic_d);
        day_icon.add(R.drawable.ic_d);
        day_icon.add(R.drawable.ic_d);
        day_icon.add(R.drawable.ic_d);
        day_icon.add(R.drawable.ic_d);
        day_icon.add(R.drawable.ic_d);
        day_icon.add(R.drawable.ic_w);
        day_icon.add(R.drawable.ic_w);
        day_icon.add(R.drawable.ic_w);
        day_icon.add(R.drawable.ic_ac);
        day_icon.add(R.drawable.ic_ac);
        day_icon.add(R.drawable.ic_ad);

        night_icon.add(R.drawable.ic_u);
        night_icon.add(R.drawable.ic_u);
        night_icon.add(R.drawable.ic_u);
        night_icon.add(R.drawable.ic_bbb);
        night_icon.add(R.drawable.ic_bbb);
        night_icon.add(R.drawable.ic_v);
        night_icon.add(R.drawable.ic_v);
        night_icon.add(R.drawable.ic_bbb);
        night_icon.add(R.drawable.ic_u);
        night_icon.add(R.drawable.ic_u);
        night_icon.add(R.drawable.ic_iii);
        night_icon.add(R.drawable.ic_iii);
        night_icon.add(R.drawable.ic_aaa);
        night_icon.add(R.drawable.ic_aaa);
        night_icon.add(R.drawable.ic_p);
        night_icon.add(R.drawable.ic_o);
        night_icon.add(R.drawable.ic_o);
        night_icon.add(R.drawable.ic_o);
        night_icon.add(R.drawable.ic_o);
        night_icon.add(R.drawable.ic_iii);
        night_icon.add(R.drawable.ic_iii);
        night_icon.add(R.drawable.ic_o);
        night_icon.add(R.drawable.ic_o);
        night_icon.add(R.drawable.ic_o);
        night_icon.add(R.drawable.ic_ccc);
        night_icon.add(R.drawable.ic_p);
        night_icon.add(R.drawable.ic_p);
        night_icon.add(R.drawable.ic_o);
        night_icon.add(R.drawable.ic_o);
        night_icon.add(R.drawable.ic_ccc);
        night_icon.add(R.drawable.ic_h);
        night_icon.add(R.drawable.ic_hhh);
        night_icon.add(R.drawable.ic_l);
        night_icon.add(R.drawable.ic_l);
        night_icon.add(R.drawable.ic_ab);
        night_icon.add(R.drawable.ic_l);
        night_icon.add(R.drawable.ic_ab);
        night_icon.add(R.drawable.ic_ab);
        night_icon.add(R.drawable.ic_a);
        night_icon.add(R.drawable.ic_af);
        night_icon.add(R.drawable.ic_j);
        night_icon.add(R.drawable.ic_af);
        night_icon.add(R.drawable.ic_j);
        night_icon.add(R.drawable.ic_j);
        night_icon.add(R.drawable.ic_j);
        night_icon.add(R.drawable.ic_j);
        night_icon.add(R.drawable.ic_ggg);
        night_icon.add(R.drawable.ic_af);
        night_icon.add(R.drawable.ic_ad);
        night_icon.add(R.drawable.ic_eee);
        night_icon.add(R.drawable.ic_fff);
        night_icon.add(R.drawable.ic_fff);
        night_icon.add(R.drawable.ic_fff);
        night_icon.add(R.drawable.ic_ddd);
        night_icon.add(R.drawable.ic_ad);
        night_icon.add(R.drawable.ic_ac);
        night_icon.add(R.drawable.ic_ad);
        night_icon.add(R.drawable.ic_l);
        night_icon.add(R.drawable.ic_eee);
        night_icon.add(R.drawable.ic_w);
        night_icon.add(R.drawable.ic_ddd);
        night_icon.add(R.drawable.ic_ddd);
        night_icon.add(R.drawable.ic_ddd);
        night_icon.add(R.drawable.ic_ddd);
        night_icon.add(R.drawable.ic_ddd);
        night_icon.add(R.drawable.ic_ddd);
        night_icon.add(R.drawable.ic_ddd);
        night_icon.add(R.drawable.ic_w);
        night_icon.add(R.drawable.ic_w);
        night_icon.add(R.drawable.ic_w);
        night_icon.add(R.drawable.ic_ac);
        night_icon.add(R.drawable.ic_ac);
        night_icon.add(R.drawable.ic_ad);

        weather_id.add(200);
        weather_id.add(201);
        weather_id.add(202);
        weather_id.add(210);
        weather_id.add(211);
        weather_id.add(212);
        weather_id.add(221);
        weather_id.add(230);
        weather_id.add(231);
        weather_id.add(232);
        weather_id.add(300);
        weather_id.add(301);
        weather_id.add(302);
        weather_id.add(310);
        weather_id.add(311);
        weather_id.add(312);
        weather_id.add(313);
        weather_id.add(314);
        weather_id.add(321);
        weather_id.add(500);
        weather_id.add(501);
        weather_id.add(502);
        weather_id.add(503);
        weather_id.add(504);
        weather_id.add(511);
        weather_id.add(520);
        weather_id.add(521);
        weather_id.add(522);
        weather_id.add(531);
        weather_id.add(600);
        weather_id.add(601);
        weather_id.add(602);
        weather_id.add(611);
        weather_id.add(612);
        weather_id.add(615);
        weather_id.add(616);
        weather_id.add(620);
        weather_id.add(621);
        weather_id.add(622);
        weather_id.add(701);
        weather_id.add(711);
        weather_id.add(721);
        weather_id.add(731);
        weather_id.add(741);
        weather_id.add(751);
        weather_id.add(761);
        weather_id.add(762);
        weather_id.add(771);
        weather_id.add(781);
        weather_id.add(800);
        weather_id.add(801);
        weather_id.add(802);
        weather_id.add(803);
        weather_id.add(804);
        weather_id.add(900);
        weather_id.add(901);
        weather_id.add(902);
        weather_id.add(903);
        weather_id.add(904);
        weather_id.add(905);
        weather_id.add(906);
        weather_id.add(951);
        weather_id.add(952);
        weather_id.add(953);
        weather_id.add(954);
        weather_id.add(955);
        weather_id.add(956);
        weather_id.add(957);
        weather_id.add(958);
        weather_id.add(959);
        weather_id.add(960);
        weather_id.add(961);
        weather_id.add(962);


        country_code.put("AF", "Afghanistan");
        country_code.put("AX", "Aland Islands");
        country_code.put("AL", "Albania");
        country_code.put("DZ", "Algeria");
        country_code.put("AS", "American Samoa");
        country_code.put("AD", "Andorra");
        country_code.put("AO", "Angola");
        country_code.put("AI", "Anguilla");
        country_code.put("AQ", "Antarctica");
        country_code.put("AG", "Antigua and Barbuda");
        country_code.put("AR", "Argentina");
        country_code.put("AM", "Armenia");
        country_code.put("AW", "Aruba");
        country_code.put("AU", "Australia");
        country_code.put("AT", "Austria");
        country_code.put("AZ", "Azerbaijan");
        country_code.put("BS", "Bahamas");
        country_code.put("BH", "Bahrain");
        country_code.put("BD", "Bangladesh");
        country_code.put("BB", "Barbados");
        country_code.put("BY", "Belarus");
        country_code.put("BE", "Belgium");
        country_code.put("BZ", "Belize");
        country_code.put("BJ", "Benin");
        country_code.put("BM", "Bermuda");
        country_code.put("BT", "Bhutan");
        country_code.put("BO", "Bolivia");
        country_code.put("BQ", "Bonaire");  // no flag
        country_code.put("BA", "Bosnia Herzegovina");
        country_code.put("BW", "Botswana");
        country_code.put("BV", "Bouvet Island");
        country_code.put("BR", "Brazil");
        country_code.put("IO", "British Indian Ocean Territory");  // no flag
        country_code.put("BN", "Brunei Darussalam");
        country_code.put("BG", "Bulgaria");
        country_code.put("BF", "Burkina Faso");
        country_code.put("BI", "Burundi");
        country_code.put("KH", "Cambodia");
        country_code.put("CM", "Cameroon");
        country_code.put("CA", "Canada");
        country_code.put("CV", "Cabo Verde");
        country_code.put("KY", "Cayman Islands");
        country_code.put("CF", "Central African Republic");
        country_code.put("TD", "Chad");
        country_code.put("CL", "Chile");
        country_code.put("CN", "China");
        country_code.put("CX", "Christmas Island");
        country_code.put("CC", "Cocos Islands");
        country_code.put("CO", "Colombia");
        country_code.put("KM", "Comoros");
        country_code.put("CG", "Congo");
        country_code.put("CD", "Congo Kinshasa");
        country_code.put("CK", "Cook Islands");
        country_code.put("CR", "Costa Rica");
        country_code.put("CI", "Ivory Coast");  //no flag
        country_code.put("HR", "Croatia");
        country_code.put("CU", "Cuba");
        country_code.put("CW", "Curaçao"); //no flag
        country_code.put("CY", "Cyprus");
        country_code.put("CZ", "Czech Republic");
        country_code.put("DK", "Denmark");
        country_code.put("DJ", "Djibouti");
        country_code.put("DM", "Dominicana");
        country_code.put("DO", "Dominican Republic");
        country_code.put("EC", "Ecuador");
        country_code.put("EG", "Egypt");
        country_code.put("SV", "El Salvador");
        country_code.put("GQ", "Equatorial Guinea");
        country_code.put("ER", "Eritrea");
        country_code.put("EE", "Estonia");
        country_code.put("ET", "Ethiopia");
        country_code.put("FK", "Falkland Islands");
        country_code.put("FO", "Faroe Islands");
        country_code.put("FJ", "Fiji");
        country_code.put("FI", "Finland");
        country_code.put("FR", "France");
        country_code.put("GF", "French Guiana");   //no flag
        country_code.put("PF", "French Polynesia");
        country_code.put("TF", "French Southern Territories");
        country_code.put("GA", "Gabon");
        country_code.put("GM", "Gambia");
        country_code.put("GE", "Georgia");
        country_code.put("DE", "Germany");
        country_code.put("GH", "Ghana");
        country_code.put("GI", "Gibraltar");
        country_code.put("GR", "Greece");
        country_code.put("GL", "Greenland");
        country_code.put("GD", "Grenada");
        country_code.put("GP", "Guadeloupe");
        country_code.put("GU", "Guam");
        country_code.put("GT", "Guatemala");
        country_code.put("GG", "Guernsey");
        country_code.put("GN", "Guinea");
        country_code.put("GW", "Guinea Bissau");
        country_code.put("GY", "Guyana");
        country_code.put("HT", "Haiti");
        country_code.put("HM", "Heard Island and McDonald Islands");  // no flag
        country_code.put("VA", "Holy See");
        country_code.put("HN", "Honduras");
        country_code.put("HK", "Hong Kong");
        country_code.put("HU", "Hungary");
        country_code.put("IS", "Iceland");
        country_code.put("IN", "India");
        country_code.put("ID", "Indonesia");
        country_code.put("IR", "Iran");
        country_code.put("IQ", "Iraq");
        country_code.put("IE", "Ireland");
        country_code.put("IM", "Isle of Man");
        country_code.put("IL", "Israel");
        country_code.put("IT", "Italy");
        country_code.put("JM", "Jamaica");
        country_code.put("JP", "Japan");
        country_code.put("JE", "Jersey");
        country_code.put("JO", "Jordan");
        country_code.put("KZ", "Kazakhstan");
        country_code.put("KE", "Kenya");
        country_code.put("KI", "Kiribati");
        country_code.put("KP", "North Korea");
        country_code.put("KR", "South Korea");
        country_code.put("KW", "Kuwait");
        country_code.put("KG", "Kyrgyzstan");
        country_code.put("LA", "Laos");
        country_code.put("LV", "Latvia");
        country_code.put("LB", "Lebanon");
        country_code.put("LS", "Lesotho");
        country_code.put("LR", "Liberia");
        country_code.put("LY", "Libya");
        country_code.put("LI", "Liechtenstein");
        country_code.put("LT", "Lithuania");
        country_code.put("LU", "Luxembourg");
        country_code.put("MO", "Macao");   //no flag
        country_code.put("MK", "Macedonia");
        country_code.put("MG", "Madagascar");
        country_code.put("MW", "Malawi");
        country_code.put("MY", "Malaysia");
        country_code.put("MV", "Maldives");
        country_code.put("ML", "Mali");
        country_code.put("MT", "Malta");
        country_code.put("MH", "Marshall Islands");
        country_code.put("MQ", "Martinique");
        country_code.put("MR", "Mauritania");
        country_code.put("MU", "Mauritius");
        country_code.put("YT", "Mayotte");
        country_code.put("MX", "Mexico");
        country_code.put("FM", "Micronesia");
        country_code.put("MD", "Moldova");
        country_code.put("MC", "Monaco");
        country_code.put("MN", "Mongolia");
        country_code.put("ME", "Montenegro");
        country_code.put("MS", "Montserrat");
        country_code.put("MA", "Morocco");
        country_code.put("MZ", "Mozambique");
        country_code.put("MM", "Myanmar");
        country_code.put("NA", "Namibia");
        country_code.put("NR", "Nauru");
        country_code.put("NP", "Nepal");
        country_code.put("NL", "Netherlands");
        country_code.put("NC", "New Caledonia");
        country_code.put("NZ", "New Zealand");
        country_code.put("NI", "Nicaragua");
        country_code.put("NE", "Niger");
        country_code.put("NG", "Nigeria");
        country_code.put("NU", "Niue");
        country_code.put("NF", "Norfolk Island");
        country_code.put("MP", "Northern Mariana Islands");
        country_code.put("NO", "Norway");
        country_code.put("OM", "Oman");
        country_code.put("PK", "Pakistan");
        country_code.put("PW", "Palau");
        country_code.put("PS", "Palestine"); //no flag
        country_code.put("PA", "Panama");
        country_code.put("PG", "Papua New Guinea");
        country_code.put("PY", "Paraguay");
        country_code.put("PE", "Peru");
        country_code.put("PH", "Philippines");
        country_code.put("PN", "Pitcairn");
        country_code.put("PL", "Poland");
        country_code.put("PT", "Portugal");
        country_code.put("PR", "Puerto Rico");
        country_code.put("QA", "Qatar");
        country_code.put("RE", "Rï¿½union"); // no flag
        country_code.put("RO", "Romania");
        country_code.put("RU", "Russian Federation");
        country_code.put("RW", "Rwanda");
        country_code.put("BL", "Saint Barthelemy");  // 2 flags
        country_code.put("SH", "Saint Helena");
        country_code.put("KN", "Saint Kitts and Nevis");
        country_code.put("LC", "Saint Lucia");
        country_code.put("MF", "Saint Martin");  // 2flags
        country_code.put("PM", "Saint Pierre and Miquelon");
        country_code.put("VC", "Saint Vincent and the Grenadines");
        country_code.put("WS", "Samoa");
        country_code.put("SM", "San Marino");
        country_code.put("ST", "Sao Tome and Principe");
        country_code.put("SA", "Saudi Arabia");
        country_code.put("SN", "Senegal");
        country_code.put("RS", "Serbia");
        country_code.put("SC", "Seychelles");
        country_code.put("SL", "Sierra Leone");
        country_code.put("SG", "Singapore");
        country_code.put("SX", "Sint Maarten (Dutch part)");   // no flag
        country_code.put("SK", "Slovakia");
        country_code.put("SI", "Slovenia");
        country_code.put("SB", "Solomon Islands");
        country_code.put("SO", "Somalia");
        country_code.put("ZA", "South Africa");
        country_code.put("GS", "South Georgia");
        country_code.put("SS", "South Sudan");     //no flag
        country_code.put("ES", "Spain");
        country_code.put("LK", "Sri Lanka");
        country_code.put("SD", "Sudan");
        country_code.put("SR", "Suriname");
        country_code.put("SJ", "Svalbard Jan Mayen");
        country_code.put("SZ", "Swaziland");
        country_code.put("SE", "Sweden");
        country_code.put("CH", "Switzerland");
        country_code.put("SY", "Syria");
        country_code.put("TW", "Taiwan");
        country_code.put("TJ", "Tajikistan");
        country_code.put("TZ", "Tanzania");
        country_code.put("TH", "Thailand");
        country_code.put("TL", "Timor Leste");
        country_code.put("TG", "Togo");
        country_code.put("TK", "Tokelau");
        country_code.put("TO", "Tonga");
        country_code.put("TT", "Trinidad and Tobago");
        country_code.put("TN", "Tunisia");
        country_code.put("TR", "Turkey");
        country_code.put("TM", "Turkmenistan");
        country_code.put("TC", "Turks and Caicos Islands");
        country_code.put("TV", "Tuvalu");
        country_code.put("UG", "Uganda");
        country_code.put("UA", "Ukraine");
        country_code.put("AE", "United Arab Emirates");
        country_code.put("GB", "United Kingdom");
        country_code.put("US", "United States of America");
        country_code.put("UM", "United States Minor Outlying Islands");  //no flag
        country_code.put("UY", "Uruguay");
        country_code.put("UZ", "Uzbekistan");
        country_code.put("VU", "Vanuatu");
        country_code.put("VE", "Venezuela");
        country_code.put("VN", "VietNam");
        country_code.put("VG", "Virgin Islands British");
        country_code.put("VI", "Virgin Islands US");
        country_code.put("WF", "Wallis and Futuna");
        country_code.put("EH", "Western Sahara");
        country_code.put("YE", "Yemen");
        country_code.put("ZM", "Zambia");
        country_code.put("ZW", "Zimbabwe");

        weather_desc.put(200, R.string.weather_200);
        weather_desc.put(201, R.string.weather_201);
        weather_desc.put(202, R.string.weather_202);
        weather_desc.put(210, R.string.weather_210);
        weather_desc.put(211, R.string.weather_211);
        weather_desc.put(212, R.string.weather_212);
        weather_desc.put(221, R.string.weather_221);
        weather_desc.put(230, R.string.weather_230);
        weather_desc.put(231, R.string.weather_231);
        weather_desc.put(232, R.string.weather_232);
        weather_desc.put(300, R.string.weather_300);
        weather_desc.put(301, R.string.weather_301);
        weather_desc.put(302, R.string.weather_302);
        weather_desc.put(310, R.string.weather_310);
        weather_desc.put(311, R.string.weather_311);
        weather_desc.put(312, R.string.weather_312);
        weather_desc.put(313, R.string.weather_313);
        weather_desc.put(314, R.string.weather_314);
        weather_desc.put(321, R.string.weather_321);
        weather_desc.put(500, R.string.weather_500);
        weather_desc.put(501, R.string.weather_501);
        weather_desc.put(502, R.string.weather_502);
        weather_desc.put(503, R.string.weather_503);
        weather_desc.put(504, R.string.weather_504);
        weather_desc.put(511, R.string.weather_511);
        weather_desc.put(520, R.string.weather_520);
        weather_desc.put(521, R.string.weather_521);
        weather_desc.put(522, R.string.weather_522);
        weather_desc.put(531, R.string.weather_531);
        weather_desc.put(600, R.string.weather_600);
        weather_desc.put(601, R.string.weather_601);
        weather_desc.put(602, R.string.weather_602);
        weather_desc.put(611, R.string.weather_611);
        weather_desc.put(612, R.string.weather_612);
        weather_desc.put(615, R.string.weather_615);
        weather_desc.put(616, R.string.weather_616);
        weather_desc.put(620, R.string.weather_620);
        weather_desc.put(621, R.string.weather_621);
        weather_desc.put(622, R.string.weather_622);
        weather_desc.put(701, R.string.weather_701);
        weather_desc.put(711, R.string.weather_711);
        weather_desc.put(721, R.string.weather_721);
        weather_desc.put(731, R.string.weather_731);
        weather_desc.put(741, R.string.weather_741);
        weather_desc.put(751, R.string.weather_751);
        weather_desc.put(761, R.string.weather_761);
        weather_desc.put(762, R.string.weather_762);
        weather_desc.put(771, R.string.weather_771);
        weather_desc.put(781, R.string.weather_781);
        weather_desc.put(800, R.string.weather_800);
        weather_desc.put(801, R.string.weather_801);
        weather_desc.put(802, R.string.weather_802);
        weather_desc.put(803, R.string.weather_803);
        weather_desc.put(804, R.string.weather_804);
        weather_desc.put(900, R.string.weather_900);
        weather_desc.put(901, R.string.weather_901);
        weather_desc.put(902, R.string.weather_902);
        weather_desc.put(903, R.string.weather_903);
        weather_desc.put(904, R.string.weather_904);
        weather_desc.put(905, R.string.weather_905);
        weather_desc.put(906, R.string.weather_906);
        weather_desc.put(951, R.string.weather_951);
        weather_desc.put(952, R.string.weather_952);
        weather_desc.put(953, R.string.weather_953);
        weather_desc.put(954, R.string.weather_954);
        weather_desc.put(955, R.string.weather_955);
        weather_desc.put(956, R.string.weather_956);
        weather_desc.put(957, R.string.weather_957);
        weather_desc.put(958, R.string.weather_958);
        weather_desc.put(959, R.string.weather_959);
        weather_desc.put(960, R.string.weather_960);
        weather_desc.put(961, R.string.weather_961);
        weather_desc.put(962, R.string.weather_962);

    }

    public ArrayList<Integer> Weather_Icon(int weatherid, long sun_rise,
            long sun_set, String currentime, String mdate) {
        boolean isDay = false;
        int isItDay = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String inputString = currentime + ":00.000";

        Date date = null;
        try {

            date = sdf.parse(mdate + " " + inputString);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (date.getTime() > sun_rise && date.getTime() < sun_set) {
            isDay = true;
            isItDay = 1;
        }

        ArrayList<Integer> result = new ArrayList<Integer>();
        result.add(isItDay);
        result.add((isDay) ? day_icon.get(weather_id.indexOf(weatherid))
                : night_icon.get(weather_id.indexOf(weatherid)));
        return result;
    }

    public boolean isSystemTwentyFourHrFormat(Context c) {
        return DateFormat.is24HourFormat(c);
    }

    public String convertToTwelveHr(String currentTime) {
        String[] hr = currentTime.split(":");

        int parsedValue = Integer.parseInt(hr[0]);
        if (parsedValue > 12) {
            parsedValue = parsedValue - 12;
        } else if (parsedValue == 0) {
            parsedValue = 12;
        }

        String tale = (Integer.parseInt(hr[0]) < 12) ? " AM" : " PM";
        return String.format("%02d", parsedValue) + ":"
                + String.format("%02d", Integer.parseInt(hr[1])) + tale;
    }

    public String convertTo24Hr(String currentTime) {
    	try{
    		String[] hr = currentTime.split(":");
            return String.format("%02d", Integer.parseInt(hr[0])) + ":"
                    + String.format("%02d", Integer.parseInt(hr[1]));	
    	}catch(Exception e){
    		return "";
    	}
        
    }

    public String convertToCelcius(String temperature) {
        double new_temp = Float.parseFloat(temperature) - 273.15;
        // return String.valueOf(new_temp).substring(0,2)+"\u2103";
        return Integer.toString((int) new_temp) + "\u2103";
    }

    public String convertToKmph(String windSpeed) {
        double new_speed = Float.parseFloat(windSpeed) * 3.6;
        NumberFormat formatter = new DecimalFormat("#0.00");
        return formatter.format(new_speed);
        // return String.format("%.2f", String.valueOf(new_speed));
    }

    public String getProperTime(String time, Context c) {
        pref = PreferenceManager.getDefaultSharedPreferences(c);
        String format = pref.getString("timeformat", "24 Hr Format");

        if (format.contains("24")) {
            return convertTo24Hr(time);
        } else if (format.contains("12")) {
            return convertToTwelveHr(time);
        } else if (format.contains("System")) {
            boolean istwentyfour = res.isSystemTwentyFourHrFormat(c);
            if (!istwentyfour) {
                time = res.convertToTwelveHr(time);
            }
            return time;
        }
        return time;
    }

    public String getProperTemperature(String temp, Context c) {
        pref = PreferenceManager.getDefaultSharedPreferences(c);
        String format = pref.getString("temperatureformat", "\u2103");

        if (format.contains("\u2103")) {
            return convertToCelcius(temp);
        }
        return temp.substring(0, 3) + "\u212A";

    }

    public String getProperWindSpeed(String speed, Context c) {
        pref = PreferenceManager.getDefaultSharedPreferences(c);
        String format = pref.getString("windpeedformat", "Kmph");

        if (format.contains("Km")) {
            return convertToKmph(speed) + " "
                    + c.getResources().getString(R.string.kmph);
        }
        return speed + " " + c.getResources().getString(R.string.mps);
    }

    public String getAppString(String value) {
        try {
            int index = res_string.indexOf(value.toLowerCase());
            return res_integer.get(index) + "";
        } catch (Exception e) {
            return value;
        }

    }
    
    public String getCountryName(String countryCode){
        String country = country_code.get(countryCode);
        if(country == null){
            country = countryCode;
        }
        return country;
    }

}
