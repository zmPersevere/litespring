package org.litespring.beans;

import org.omg.CORBA.OBJ_ADAPTER;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @Description :
 * @Author : zhangMing
 * @Date : Created in 8:19 PM 2019/10/11
 */
public class ConstructorArgument {

    private final List<ValueHolder> argumentValues = new LinkedList<ValueHolder>();

    public ConstructorArgument(){

    }

    public void addArgumentValue(Object value , String type){
        this.argumentValues.add(new ValueHolder(value,type));
    }

    public void addArgumentValue(ValueHolder valueHolder){
        this.argumentValues.add(valueHolder);
    }

    public void addArgumentValue(Object value){
        this.argumentValues.add(new ValueHolder( value ));
    }

    public List<ValueHolder> getArgumentValues(){
        return Collections.unmodifiableList(this.argumentValues);
    }

    public int getArgumentCount(){
        return this.argumentValues.size();
    }

    public boolean isEmpty(){
        return this.argumentValues.isEmpty();
    }

    public void clear(){
        this.argumentValues.clear();
    }

    public static class ValueHolder{

        private Object value ;
        private String type;
        private String name;

        public ValueHolder(Object value){
            this.value = value;
        }

        public ValueHolder(Object value , String type){
            this.value = value ;
            this.type = type ;
        }

        public ValueHolder(Object value , String type , String name){
            this.value = value ;
            this.type = type ;
            this.name = name ;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
