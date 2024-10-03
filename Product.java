public class Product {

    private int quant;
    private int valor;


    public Product( int quant, int valor){
        this.quant = quant;
        this.valor = valor;
    }

    public int getQuant() {
        return quant;
    }

    public void setQuant(int quant) {
        this.quant = quant;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
