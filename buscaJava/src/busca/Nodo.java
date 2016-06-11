package busca;

import java.util.LinkedList;
import java.util.List;

/**
 * Representa um nodo da �rvore de busca
 */
public class Nodo implements Comparable<Nodo> {

	Estado estado; // o estado
	Nodo pai; // o pai
	int profundidade = 0;
	int g = 0; // custo de ter gerado o nodo (todo o caminho)
	int f = -1; // f = g + h
	private final LinkedList<Nodo> sucessores = new LinkedList<Nodo>();

	public Nodo(Estado e, Nodo p) {
		estado = e;
		pai = p;
		if (p == null) {
			profundidade = 0;
			g = e.custo();
		} else {
			profundidade = p.getProfundidade() + 1;
			g = e.custo() + p.g;
		}
	}

	public int getProfundidade() {
		return profundidade;
	}

	public Estado getEstado() {
		return estado;
	}

	public Nodo getPai() {
		return pai;
	}

	/**
	 * retorna o custo acumulado de gerar o nodo (baseado no acumulo do custo de
	 * gerar os estados)
	 */
	public int g() {
		return g;
	}

	/**
	 * Custo total
	 */
	public int f() {
		if (f == -1) {
			f = g + ((Heuristica) estado).h();
		}
		return f;
	}

	void invertePaternidade() {
		if (pai.pai != null) {
			pai.invertePaternidade();
		}
		pai.pai = this;
	}

	/**
	 * arruma a profundidade de um nodo e de seus pais
	 */
	void setProfundidade() {
		if (pai == null) {
			profundidade = 0;
		} else {
			pai.setProfundidade();
			profundidade = pai.getProfundidade() + 1;
		}
	}

	/**
	 * testa se o nodo n�o tem um ascensor igual a ele (se um dos pais eh igual
	 * a ele)
	 */
	boolean ehDescendenteNovo(Nodo ascensor) {
		if (ascensor == null) {
			return true;
		} else {
			if (ascensor.estado.equals(this.estado)) {
				return false;
			} else {
				return ehDescendenteNovo(ascensor.pai);
			}
		}
	}

	/**
	 * se dois nodos s�o iguais (por enquato, s� verifica se os estados s�o
	 * iguais -- usado no bi-direcional)
	 */
	public boolean equals(Object o) {
		try {
			Nodo n = (Nodo) o;
			return this.estado.equals(n.estado);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/** utiliza o custo (g) como elemento de ordena��o */
	public int compareTo(Nodo outro) {
		if (g > outro.g) {
			return 1;
		} else if (g == outro.g) {
			return 0;
		}
		return -1;
	}

	/**
	 * imprime o caminho at� a ra�z
	 */
	public String montaCaminho() {
		return montaCaminho(this);
	}

	public String montaCaminho(Nodo n) {
		if (n != null) {
			return montaCaminho(n.pai) + n + "; ";
		}
		return "";
	}

	protected LinkedList<Nodo> getSucessores() {
		return sucessores;
	}

	protected void addSucessores(List<Nodo> sucessores) {
		this.sucessores.addAll(sucessores);
	}

	protected void addSucessor(Nodo sucessores) {
		this.sucessores.add(sucessores);
	}

	protected void deletarSucessores() {
		this.sucessores.clear();
	}

	public String toString() {
		return estado.toString();
	}
}
