@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;

    // GET: Listar todos
    @GetMapping
    public List<Produto> listar() {
        return repository.findAll();
    }

    // GET: Buscar por ID com tratamento de 404
    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        Optional<Produto> produto = repository.findById(id);
        
        if (produto.isPresent()) {
            return ResponseEntity.ok(produto.get());
        }
        return ResponseEntity.notFound().build(); // Retorna 404 se não encontrar
    }

    // POST: Criar com status 201 Created
    @PostMapping
    public ResponseEntity<Produto> criar(@RequestBody Produto produto) {
        Produto salvo = repository.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // DELETE: Remover com tratamento de 404
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build(); // Retorna 404 se não existir
        }
        
        repository.deleteById(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content após deletar
    }
}
