import kotlin.random.Random

class Snake(private val gridWidth: Int, private val gridHeight: Int){
    private val plateau:Array<IntArray> = Array(gridWidth) { IntArray(gridHeight) {0} }
    private var dirrection:Int = 1
    private var headPos:Vector2Int = Vector2Int(2,0)
    private var tailPos:Vector2Int = Vector2Int(0,0)
    private var applePos:Vector2Int = Vector2Int(0,0)

    init{
        plateau[0][0] = 1;
        plateau[1][0] = 1;

        placeApple()
    }

    //fonctions pour faire fonctionner le programme

    private fun placeApple(){
        //todo verification si la grille est full
        var nx = 0;
        var ny = 0;
        do{
            nx = Random.nextInt(0,gridWidth)
            ny = Random.nextInt(0,gridHeight)
        }while(plateau[nx][ny] != 0)
        applePos = Vector2Int(nx,ny)
    }

    private fun isDead(x:Int, y:Int):Boolean{
        if (x < 0 || x >= gridWidth || y < 0 || y >= gridHeight) return true
        return plateau[x][y] != 0
    }

    private fun tailForward(){
        when(plateau[tailPos.x][tailPos.y]){
            1 -> {
                plateau[tailPos.x][tailPos.y] = 0
                tailPos.x++
            }
            2 -> {
                plateau[tailPos.x][tailPos.y] = 0
                tailPos.y++
            }
            3 -> {
                plateau[tailPos.x][tailPos.y] = 0
                tailPos.x--
            }
            4 -> {
                plateau[tailPos.x][tailPos.y] = 0
                tailPos.y--
            }
        }
    }

    fun changeOrientation(newOr : Int){
        if((newOr + 1) % 4 != dirrection-1){
            dirrection = newOr;
        }
    }


    fun iteration():Boolean{
        when(dirrection){
            1 -> {
                //test si mort
                if(isDead(headPos.x + 1,headPos.y)) return false
                plateau[headPos.x][headPos.y] = dirrection
                //test si pomme
                headPos.x ++

                if(headPos == applePos){
                    //miam miam
                    placeApple()
                }else{
                    //on avance
                    tailForward()
                }
            }
            2 -> {
                //test si mort
                if(isDead(headPos.x,headPos.y +1)) return false
                plateau[headPos.x][headPos.y] = dirrection
                //test si pomme
                headPos.y ++

                if(headPos == applePos){
                    //miam miam
                    placeApple()
                }else{
                    //on avance
                    tailForward()
                }
            }
            3 -> {
                //test si mort
                if(isDead(headPos.x - 1,headPos.y)) return false
                plateau[headPos.x][headPos.y] = dirrection
                //test si pomme
                headPos.x --

                if(headPos == applePos){
                    //miam miam
                    placeApple()
                }else{
                    //on avance
                    tailForward()
                }
            }
            4 -> {
                //test si mort
                if(isDead(headPos.x,headPos.y - 1)) return false
                plateau[headPos.x][headPos.y] = dirrection
                //test si pomme
                headPos.y --

                if(headPos == applePos){
                    //miam miam
                    placeApple()
                }else{
                    //on avance
                    tailForward()
                }
            }
        }
        return true
    }

    //fonctions pour l'affichage
    fun getHeadPos():Vector2Int = headPos
    fun getTailPos():Vector2Int = tailPos
    fun getApplePos():Vector2Int = applePos
    fun getPlateau(x:Int,y:Int):Int = plateau[x][y]
}