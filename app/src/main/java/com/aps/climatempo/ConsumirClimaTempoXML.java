package com.aps.climatempo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class ConsumirClimaTempoXML {

    public static List<ClimaTempo> xmlDados(String conteudo){
        try {

            boolean dadosNaTag = false;
            String tagAtual = "";
            ClimaTempo tempo = null;
            List<ClimaTempo> tempoList = new ArrayList<>();

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(conteudo));

            int eventType = xmlPullParser.getEventType();

            while(eventType != xmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        tagAtual = xmlPullParser.getName();
                        if (tagAtual.endsWith("previsao")) {
                            dadosNaTag = true;
                            tempo = new ClimaTempo();
                            tempoList.add(tempo);

                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (xmlPullParser.getName().equals("previsao")) {
                            dadosNaTag = false;
                        }
                        tagAtual = "";
                        break;
                    case XmlPullParser.TEXT:
                        if (dadosNaTag && tempo != null) {
                            switch (tagAtual) {
                                case "nome":
                                    tempo.setNome(xmlPullParser.getText());
                                    break;
                                case "uf":
                                    tempo.setUf(xmlPullParser.getText());
                                    break;
                                case "atualizacao":
                                    tempo.setAtualizacao(xmlPullParser.getText());
                                    break;
                                case "dia":
                                    tempo.setDia(xmlPullParser.getText());
                                    break;
                                case "maxima":
                                    tempo.setMaxima(xmlPullParser.getText());
                                    break;
                                case "minima":
                                    tempo.setMinima(xmlPullParser.getText());
                                    break;
                                case "iuv":
                                    tempo.setIuv(xmlPullParser.getText());
                                    break;

                            }
                        }

                        break;
                }
                eventType = xmlPullParser.next();
            }

            return tempoList;

        }catch (Exception e){
            e.getStackTrace();
            return null;
        }
    }
}