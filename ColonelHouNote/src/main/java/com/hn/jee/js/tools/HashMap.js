var HashMap = function()
{
	this.size = 0;
	this.entry = new Object();
};

HashMap.prototype.put = function(key, value)
{
	if(!this.containKey(key))
	{
		this.size ++;
	}
	this.entry[key] = value;
};

HashMap.prototype.get = function(key)
{
	return this.containKey(key) ? this.entry : null;
};

HashMap.prototype.remove = function(key)
{
	if(this.containKey(key) && (delete this.entry[key]))
	{
		return true;
	}
	return false;
};
HashMap.prototype.containsKey = function ( key )   
{   
    return (key in entry);   
};

HashMap.prototype.containsValue = function ( value )   
{   
    for(var prop in entry)   
    {   
        if(entry[prop] == value)   
        {   
            return true;   
        }   
    }   
    return false;   
};

HashMap.prototype.values = function ()   
{   
    var values = new Array();   
    for(var prop in entry)   
    {   
        values.push(entry[prop]);   
    }   
    return values;   
}; 
   
/** 所有 Key **/  
HashMap.prototype.keys = function ()   
{   
    var keys = new Array();   
    for(var prop in entry)   
    {   
        keys.push(prop);   
    }   
    return keys;   
};   
   
/** Map Size **/  
HashMap.prototype.size = function ()   
{   
    return size;   
};

HashMap.prototype.clear = function()
{
	this.size = 0;
	this.entry = new Object();
};