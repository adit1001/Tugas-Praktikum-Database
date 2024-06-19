
package tugas;

import java.sql.*;
import java.util.Scanner;

public class Tugas {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1/perpustakaan";
    static final String USER = "root";
    static final String PASS = "";

    static Connection conn;
    static Statement stmt;
    static ResultSet rs;

    //MAIN PROGRAM
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean ProgJal = true;

        //MENU
        do{
        clearConsole();
        System.out.println("\n1. Input Data \n2. Tampil Data \n3. Hapus Data \n4. Update Data \n5. Keluar \nPilih Opsi:");
        int pilih = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        switch(pilih) {
            case 1:
                insert(scanner);
                break;
            case 2:
                show();
                break;
            case 3:
                delete(scanner);
                break;
            case 4:
                update(scanner);
                break;
            case 5:
                ProgJal = false;
                break;
            default:
                System.out.println("Opsi tidak valid.");
        }
        if(ProgJal){
        System.out.println("Apakah ingin lanjut [Y/N]:");
        String pilihan = scanner.nextLine();
            if(pilihan.equalsIgnoreCase("N")){ 
                ProgJal = false; //JIKA USER MEMILIH N/n MAKA PROGRAM BERHENTI
            }
        }
        }while(ProgJal);
        
        System.out.println("Program Selesai.");
        scanner.close();
    }
    
    //METHOD CLEAR CONSOLE KARENA DI NETBEANS SAYA TIDAK TAU CARA CLEAR CONSOLE
    public static void clearConsole() {
    for (int i = 0; i < 50; i++) {
        System.out.println();
    }
}

    //METHOD DELETE DATA
    public static void delete(Scanner scanner) {
        System.out.print("Masukkan ID buku yang akan dihapus: ");
        String id = scanner.nextLine();

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "DELETE FROM buku WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();
            
            System.out.println("Buku dengan ID " + id + " telah dihapus.");
            
            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //METHOD INPUT DATA
    public static void insert(Scanner scanner) {
        System.out.print("Masukkan ID buku: ");
        String id = scanner.nextLine();
        System.out.print("Masukkan judul buku: ");
        String judul_buku = scanner.nextLine();
        System.out.print("Masukkan tahun terbit: ");
        String tahun_terbit = scanner.nextLine();
        System.out.print("Masukkan stok: ");
        int stok = scanner.nextInt();
        System.out.print("Masukkan ID penulis: ");
        int penulis = scanner.nextInt();

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "INSERT INTO buku (id, judul_buku, tahun_terbit, stok, penulis) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, id);
            ps.setString(2, judul_buku);
            ps.setString(3, tahun_terbit);
            ps.setInt(4, stok);
            ps.setInt(5, penulis);

            ps.executeUpdate();

            System.out.println("Buku berhasil ditambahkan.");

            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //METHOD UPDATE DATA
    public static void update(Scanner scanner) {
        System.out.print("Masukkan ID buku yang akan diupdate: ");
        String id = scanner.nextLine();

        System.out.print("Masukkan judul buku baru: ");
        String judul_buku = scanner.nextLine();
        System.out.print("Masukkan tahun terbit baru: ");
        String tahun_terbit = scanner.nextLine();
        System.out.print("Masukkan stok baru: ");
        int stok = scanner.nextInt();
        System.out.print("Masukkan ID penulis baru: ");
        int penulis = scanner.nextInt();

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            String sql = "UPDATE buku SET judul_buku = ?, tahun_terbit = ?, stok = ?, penulis = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, judul_buku);
            ps.setString(2, tahun_terbit);
            ps.setInt(3, stok);
            ps.setInt(4, penulis);
            ps.setString(5, id);

            ps.executeUpdate();

            System.out.println("Buku dengan ID " + id + " berhasil diupdate.");

            ps.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //METHOD TAMPIL DATA
    public static void show() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();

            rs = stmt.executeQuery("SELECT * FROM buku");
            int i = 1;
            while (rs.next()) {
                System.out.println("");
                System.out.println("Data ke-" + i);
                System.out.println("ID buku: " + rs.getString("id"));
                System.out.println("Judul Buku: " + rs.getString("judul_buku"));
                System.out.println("Tahun Terbit: " + rs.getString("tahun_terbit"));
                System.out.println("Stok: " + rs.getInt("stok"));
                System.out.println("Penulis: " + rs.getInt("penulis"));
                i++;
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
