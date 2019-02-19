package app;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

public class TextApp {
	public static void main(String args[]) {
		Map<String, Integer> mapirajTextDatotetku = textMapa("C:\\datoteka.txt");
		System.out.println("Lista rijeèi iz file-a i njihov broj ponavljanja:");
		List<Entry<String, Integer>> lista = sortirajVrijednostiPoBrojuPonavljanja(mapirajTextDatotetku);
		for (Map.Entry<String, Integer> ulazak : lista) {
			System.out.println(ulazak.getKey() + " " + ulazak.getValue());
		}
	}

	public static List<Entry<String, Integer>> sortirajVrijednostiPoBrojuPonavljanja(
			Map<String, Integer> mapirajTextDatotetku) {
		Set<Entry<String, Integer>> uneseniStringovi = mapirajTextDatotetku.entrySet();
		List<Entry<String, Integer>> lista = new ArrayList<>(uneseniStringovi);
		Collections.sort(lista, new Comparator<Map.Entry<String, Integer>>() {
			@Override
			public int compare(Map.Entry<String, Integer> x, Map.Entry<String, Integer> y) {
				return (y.getValue()).compareTo(x.getValue());
			}
		});
		return lista;
	}

	public static Map<String, Integer> textMapa(String imeDatoteke) {
		Map<String, Integer> mapirajTextDatotetku = new HashMap<>();

		try (FileInputStream inputiDatoteke = new FileInputStream(imeDatoteke);
				DataInputStream podaciDatotetke = new DataInputStream(inputiDatoteke);
				BufferedReader buffer = new BufferedReader(new InputStreamReader(podaciDatotetke))) {
			Pattern obrazac = Pattern.compile("\\s+");
			String line = null;

			while ((line = buffer.readLine()) != null) {
				line = line.toLowerCase();
				String[] rijeci = obrazac.split(line);
				for (String rijec : rijeci) {
					if (mapirajTextDatotetku.containsKey(rijec)) {
						mapirajTextDatotetku.put(rijec, (mapirajTextDatotetku.get(rijec) + 1));
					} else {
						mapirajTextDatotetku.put(rijec, 1);
					}
				}
			}
		} catch (IOException error) {
			error.printStackTrace();
		}
		return mapirajTextDatotetku;
	}

}