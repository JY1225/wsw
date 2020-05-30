package com.sws.events;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.sws.base.Log;

public class SWSEventManager 
{
	private static ConcurrentLinkedQueue<SWSEventListener> listeners;

    /**
     * 添加事件
     */
    public static void addListener(SWSEventListener listener) 
    {
		if (listeners == null) {
            listeners = new ConcurrentLinkedQueue<SWSEventListener>();
        }
        listeners.add(listener);
    }

    /**
     * 移除事件
     */
    public static void removeListener(SWSEventListener listener) 
    {
        if (listeners == null)
            return;
    	listeners.remove(listener);
    }
        
    /**
     * 通知所有的Listener
     */
    public static void notifyListeners(SWSEvent event) {
        try  
        {
	    	if(listeners != null)
	    	{	
	    		//Collection listenersTmp = new HashSet();;
	    		ConcurrentLinkedQueue<SWSEventListener> listenersTmp = new ConcurrentLinkedQueue<SWSEventListener>();
		        Iterator<SWSEventListener> iter = listeners.iterator();
		        while (iter.hasNext())
		        {
		        	listenersTmp.add(iter.next());
		        }
		        
		        Iterator<SWSEventListener> iterTmp = listenersTmp.iterator();
		        while (iterTmp.hasNext()) 
		        {		    		
		        	SWSEventListener listener = (SWSEventListener) iterTmp.next();	
		            listener.lmsTransferEvent(event);
		        }
	    	}
        }
        catch (UnsatisfiedLinkError e)  
        {  
        	Log.exceptionDialog(e);
        }
        catch(LinkageError e)
        {
        	Log.exceptionDialog(e);
	        System.exit(0);   
        }
		catch(OutOfMemoryError e)
        {
			Log.exceptionDialog(e);
        }
        catch (Exception e)  
        {  
        	Log.exceptionDialog(e);
        }  
    }
    
	public void sendEvent(String eventType) {
		SWSEvent event = new SWSEvent(this, eventType);
		SWSEventManager.notifyListeners(event);				
	}

	public void sendEvent(String eventType, HashMap eventExtra) {
		SWSEvent event = new SWSEvent(this, eventType);
		event.putEventExtra(eventExtra);
		SWSEventManager.notifyListeners(event);				
	}
}
