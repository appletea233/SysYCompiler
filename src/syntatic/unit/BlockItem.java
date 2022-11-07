package syntatic.unit;

import base.BaseUnit;
import syntatic.SynUnit;

import static base.Type.*;

public class BlockItem extends SynUnit {
    public boolean isVoid = false;
    public boolean isFunc = false;
    public boolean isLoop = false;


    public BlockItem(BaseUnit parent) {
        super(parent, "BlockItem");
        this.isVoid = ((Block) parent).isVoid;
        this.isFunc = ((Block) parent).isFunc;
        this.isLoop = ((Block) parent).isLoop;
    }

    public void construct(){
        init();

        if (isSymNowEql(INTTK) || isSymNowEql(CONSTTK)){
            checkSynUnit(DECL);
        }
        else{
            // TODO: todo
            checkSynUnit(STMT);
        }
    }
}
